/*
 *
 *   Copyright (C) 2019 Truongpx Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.truongpx.ckcportfolio.features.basecrypto.uilayer

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager.widget.ViewPager
import androidx.work.WorkStatus
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.core.exception.Failure
import com.truongpx.ckcportfolio.core.extension.*
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.core.platform.BaseFragment
import com.truongpx.ckcportfolio.databinding.FragmentCryptoBinding
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.UpdateCryptoHoldingPriceUC
import com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel.CryptoViewModel
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.adapter.CryptoNormalAdapter
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.adapter.ViewPagerAdapter
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.customview.CryptoRadioButton
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog.DialogButtonInfo
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.createportfolio.uilayer.CreatePortfolioFragment
import com.truongpx.ckcportfolio.services.firebase.FirebaseAnalyticService
import kotlinx.android.synthetic.main.item_cryptolist_header.view.*
import javax.inject.Inject


class CryptoFragment : BaseFragment() {

    private lateinit var binding: FragmentCryptoBinding

    private lateinit var model: CryptoViewModel

    private var currentSelectedHeaderButton: CryptoRadioButton? = null

    lateinit var viewPagerAdapter: ViewPagerAdapter


    private var pageInLastPosition = false

    private var needReportUCResult = false

    @Inject
    lateinit var updateCryptoHoldingPriceUC: UpdateCryptoHoldingPriceUC

    @Inject
    lateinit var portfolioManager: PortfolioManager

    var handler = Handler()

    private val requestApiPeriodicly = object : Runnable {
        override fun run() {
            updateCryptoHoldingPriceUC(UseCase.None()) {
                it.either(
                    ::handleFailure
                ) {}
            }
            handler.postDelayed(this, 10000)
        }
    }

    @Inject
    lateinit var cryptoNormalAdapter: CryptoNormalAdapter


    private var cryptoList: MutableList<CryptoHoldingModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCryptoBinding.inflate(inflater, container, false)
        model = viewModel(viewModelFactory) {
            observe(holdingFull, ::onCryptoHoldingListChanged)
//            observe(liveDataWorkInfo, ::getWorkInfo)
            failure(failure, ::handleFailure)
        }

        binding.listCrypto.adapter = cryptoNormalAdapter

        setUpViewPager()
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            portfolioManager
        )
        binding.viewpager.adapter = viewPagerAdapter


        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (viewPagerAdapter.isLastItem(position)) {
                    binding.swipeRefresh.visibility = View.GONE
                    binding.buttonCreatePortfolio.visibility = View.VISIBLE
                    pageInLastPosition = true
                } else {
                    var currentPortfolioId = viewPagerAdapter.getPortfolioData(position).id
                    portfolioManager.setCurrentSelectedPortfolioId(currentPortfolioId)
                    updatePortfolioListView()
                    pageInLastPosition = false
                }
            }
        })


        binding.swipeRefresh.apply {
            setOnRefreshListener {
                handler.post(requestApiPeriodicly)
                needReportUCResult=true
                isRefreshing = false
            }
        }


        binding.cryptolistHeader.radio_cryptolist_header.setOnItemClickListener {
            sortCryptoList(it)
        }


        binding.listCrypto.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        binding.buttonCreatePortfolio.setOnClickListener {
            addFragment(CreatePortfolioFragment().apply { setCryptoFragment(this@CryptoFragment) })
        }

        setHasOptionsMenu(true)

        updateUI()

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        handler.post(requestApiPeriodicly)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(requestApiPeriodicly)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.header_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentPortfolio = portfolioManager.getCurrentSelectedPortfolio()
        return when (item.itemId) {
            R.id.menu_delete_portfolio -> {
                val deleteButtonInfo = DialogButtonInfo(
                    buttonText = getString(R.string.dialog_button_delete),
                    buttonListener = {
                        if (portfolioManager.getCurrentSelectedPortfolioId() == 0) {
                            messageHandler.showError(getString(R.string.dialog_msg_fail_delete))
                        } else {
                            portfolioManager.deleteCurrentPortfolio()
                            binding.viewpager.setCurrentItem(
                                viewPagerAdapter.getCurrentSelectedIndex(),
                                true
                            )
                            updateUI()
                            FirebaseAnalyticService.getInstance(context!!)
                                .logEventDeletePortfolio()
                        }

                    })
                messageHandler.showConfirmation(
                    getString(R.string.dialog_msg_delete, currentPortfolio?.name),
                    deleteButtonInfo
                )
                true
            }
            R.id.menu_clear_portfolio -> {
                val clearButtonInfo = DialogButtonInfo(
                    buttonText = getString(R.string.dialog_button_clear),
                    buttonListener = {
                        portfolioManager.clearCurrentPortfolio()
                    })
                messageHandler.showConfirmation(
                    getString(R.string.dialog_msg_clear, currentPortfolio?.name)
                    , clearButtonInfo
                )
                true
            }
            R.id.menu_share_screenshot -> {
                messageHandler.showMessage(getString(R.string.msg_underdevelopment))

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    fun setUpViewPager() {
        val pageOffset = 3
        // Visible part of sibling pages at the edges in dp
        val sidePageVisibleWidth = 17
        // Horizontal padding will be
        val horPadding = pageOffset + sidePageVisibleWidth
        // Apply parameters
        binding.viewpager.clipToPadding = false
        binding.viewpager.pageMargin = dpToPx(pageOffset)
        binding.viewpager.setPadding(dpToPx(horPadding), 0, dpToPx(horPadding), 0)
    }

    fun dpToPx(dp: Int): Int {
        val density = context?.resources?.displayMetrics?.density
        return Math.round(dp.toFloat() * (density ?: 0f))
    }

    fun updateUI() {
        updatePortfolioPagerOnDataChanged()
        updatePortfolioListView()
        pageInLastPosition = false
    }

    fun updatePortfolioPagerOnDataChanged() {
        viewPagerAdapter.updatePortfolioDataListChanged()
    }

    fun updatePortfolioListView() {
        val data = viewPagerAdapter.getCurrentHoldingData()
        this@CryptoFragment.cryptoList = data.toMutableList()
        currentSelectedHeaderButton?.let {
            sortCryptoList(it)
        }
        cryptoNormalAdapter.submitList(cryptoList)
        if (viewPagerAdapter.getCurrentSelectedIndex() < viewPagerAdapter.count - 1) {
            binding.buttonCreatePortfolio.visibility = View.GONE
            binding.swipeRefresh.visibility = View.VISIBLE
        }

    }

    private fun getWorkInfo(infolist: List<WorkStatus>?) {
        if (infolist.isNullOrEmpty()) return
        if (infolist[0].state.isFinished) {
            notify(getString(R.string.msg_loadworkmanager))
        }
    }


    private fun onCryptoHoldingListChanged(holdingList: List<CryptoHoldingModel>?) {
        if (pageInLastPosition) return
        viewPagerAdapter.setHoldingData(holdingList!!)
        updateUI()
        binding.viewpager.currentItem = viewPagerAdapter.getCurrentSelectedIndex()
    }


    private fun sortCryptoList(view: View) {
        if (view is CryptoRadioButton) {
            when (view.id) {
                R.id.title_name -> {
                    if (view.IsStateDown) cryptoList?.sortWith(compareBy { it.crypto_name }) else cryptoList?.sortWith(
                        compareByDescending { it.crypto_name })
                }
                R.id.title_price -> {
                    if (view.IsStateDown) cryptoList?.sortWith(compareBy { it.percent_change_24 }) else cryptoList?.sortWith(
                        compareByDescending { it.percent_change_24 })
                }
                R.id.title_holding -> {
                    if (view.IsStateDown) cryptoList?.sortWith(compareBy { it.holdingValue }) else cryptoList?.sortWith(
                        compareByDescending { it.holdingValue })
                }
                else -> false
            }
            currentSelectedHeaderButton = view
        }

        cryptoNormalAdapter.notifyDataSetChanged()
    }


    private fun handleFailure(failure: Failure?) {
        if (!needReportUCResult) return
        when (failure) {
            is Failure.NetworkConnection -> {
                messageHandler.showError(getString(R.string.failure_network_connection))
            }
            is Failure.ServerError -> {
                messageHandler.showError(getString(R.string.failure_server_error))
            }
            is Failure.OtherError ->{
                messageHandler.showError(getString(R.string.other_error))
            }
        }
        needReportUCResult = false
    }


}



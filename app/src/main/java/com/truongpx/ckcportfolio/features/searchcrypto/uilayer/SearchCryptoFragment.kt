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

package com.truongpx.ckcportfolio.features.searchcrypto.uilayer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.core.exception.Failure
import com.truongpx.ckcportfolio.core.extension.close
import com.truongpx.ckcportfolio.core.extension.failure
import com.truongpx.ckcportfolio.core.extension.observe
import com.truongpx.ckcportfolio.core.extension.viewModel
import com.truongpx.ckcportfolio.core.platform.BaseFragment
import com.truongpx.ckcportfolio.databinding.FragmentSearchCryptoBinding
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.customview.CustomHeader
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel
import com.truongpx.ckcportfolio.features.searchcrypto.presentationlayer.viewmodel.SearchCryptoViewModel
import com.truongpx.ckcportfolio.features.searchcrypto.uilayer.adapter.CryptoSearchAdapter
import com.truongpx.ckcportfolio.services.firebase.FirebaseAnalyticService
import javax.inject.Inject

class SearchCryptoFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchCryptoBinding

    private lateinit var model: SearchCryptoViewModel

    @Inject
    lateinit var adapter: CryptoSearchAdapter

    @Inject
    lateinit var portfolioManager: PortfolioManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        FirebaseAnalyticService.getInstance(context!!).logEventEnterSearchScreen()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchCryptoBinding.inflate(inflater, container, false)

        val customHeader = binding.header as CustomHeader


        var currentSelectedPortfolio = portfolioManager.getCurrentSelectedPortfolio()

        customHeader.setHeaderTitle(currentSelectedPortfolio?.name ?: getString(R.string.noportfolio))

        customHeader.setHeaderListener { }

        customHeader.setRightButtonListner {
            close()
        }


        model = viewModel(viewModelFactory) {
            observe(searchResult, ::onSearchResultReceived)
            failure(failure, ::handleFailure)
        }


        binding.listResult.adapter = adapter

        adapter.addHoldingItemSuccessListener = {
            messageHandler.showMessage(
                getString(
                    R.string.dialog_msg_add_succeed,
                    it.name,
                    currentSelectedPortfolio?.name
                )
            )
        }

        binding.listResult.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.searchCrypto(s.toString())
            }

        })


        return binding.root
    }


    private fun onSearchResultReceived(result: List<CryptoInfoModel>?) {
        adapter.submitList(result)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            Failure.NetworkConnection -> {
                messageHandler.showError(getString(R.string.failure_network_connection)); close()
            }
            Failure.ServerError -> {
                messageHandler.showError(getString(R.string.failure_server_error)); close()
            }
            is Failure.OtherError -> {
                messageHandler.showError(getString(R.string.other_error))
            }
        }
    }


}
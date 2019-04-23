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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.truongpx.corelibrary.extension.viewModel
import com.truongpx.corelibrary.platform.AbstractFragment
import com.truongpx.ckcportfolio.databinding.FragmentPagerPorfolioBinding
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel.PagerHoldingViewModel
import com.truongpx.ckcportfolio.platform.BaseFragment

class PagerItemPorfolioFragment : BaseFragment() {

    lateinit var binding: FragmentPagerPorfolioBinding

    lateinit var viewModel: PagerHoldingViewModel

    private var portfolio: List<CryptoHoldingModel> = arrayListOf()

    private var portfolioName = ""

    private var isEmptyView = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPagerPorfolioBinding.inflate(inflater, container, false)
        viewModel = viewModel(viewModelFactory) {
        }
        viewModel.setData(portfolioName, portfolio)

        binding.dataHolding = viewModel
        binding.textValueChange.bindTo(viewModel.valueChange24)

        if (isEmptyView)
            binding.rootLayout.forEach {
                it.visibility = View.INVISIBLE
            }

        return binding.root
    }


    fun setData(name: String, portfolioList: List<CryptoHoldingModel>) {
        this.portfolio = portfolioList
        this.portfolioName = name
    }

    fun setUpEmpty() {
        isEmptyView = true
    }

    companion object {
        fun newInstance(portfolioName: String, portfolio: List<CryptoHoldingModel>): PagerItemPorfolioFragment {
            val fragment = PagerItemPorfolioFragment()
            fragment.setData(portfolioName, portfolio)
            return fragment
        }
    }

}








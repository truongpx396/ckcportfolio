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

package com.truongpx.ckcportfolio.features.createportfolio.uilayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.truongpx.ckcportfolio.core.extension.close
import com.truongpx.ckcportfolio.core.extension.setUpAnimation
import com.truongpx.ckcportfolio.core.platform.BaseFragment
import com.truongpx.ckcportfolio.databinding.FragmentCreatePortfolioBinding
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.CryptoFragment
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.customview.CustomHeader
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioModel
import com.truongpx.ckcportfolio.services.firebase.FirebaseAnalyticService
import javax.inject.Inject


class CreatePortfolioFragment : BaseFragment() {

    private lateinit var cryptoFragment: CryptoFragment

    @Inject
    lateinit var portfolioManager: PortfolioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        FirebaseAnalyticService.getInstance(context!!).logEventEnterCreatePortfolioScren()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentCreatePortfolioBinding.inflate(inflater, container, false)
        val header = binding.header as CustomHeader
        header.setHeaderTitle("Create Portfolio")
        header.setRightButtonListner {
            close()
        }

        binding.buttonCreatePortfolio.setUpAnimation()

        binding.buttonCreatePortfolio.setOnClickListener {


            var inputText = binding.editName.text.toString()
            if (!inputText.isNullOrEmpty()) {
                val portfolioList = portfolioManager.getPortfolioList()
                if (!portfolioList.map { portfolioData -> portfolioData.name }.contains(inputText)) {
                    var newIndex = portfolioList.map { portfolioData -> portfolioData.id }.max()!! + 1
                    var newPortfolio =
                        PortfolioModel(
                            id = newIndex,
                            name = inputText
                        )
                    portfolioManager.addNewPortfolio(newPortfolio)
                    messageHandler.showMessage("Create Portfolio Successfully!!")
                    cryptoFragment.updateUI()
                    close()
                } else messageHandler.showError("Portfolio Name existed !")

            } else messageHandler.showError("Please enter a name")
        }
        return binding.root
    }

    fun setCryptoFragment(cryptoFragment: CryptoFragment) {
        this.cryptoFragment = cryptoFragment
    }
}
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
import com.truongpx.ckcportfolio.R
import com.truongpx.corelibrary.extension.close
import com.truongpx.corelibrary.platform.AbstractFragment
import com.truongpx.ckcportfolio.databinding.FragmentUpdateHoldingItemBinding
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.DeleteHoldingItemUC
import com.truongpx.ckcportfolio.features.searchcrypto.domainlayer.usecases.InsertIntoHoldingsUC
import com.truongpx.ckcportfolio.platform.BaseFragment
import javax.inject.Inject

class UpdateHoldingItemFragment : BaseFragment() {

    lateinit var binding: FragmentUpdateHoldingItemBinding

    @Inject
    lateinit var deleteHoldingItemUC: DeleteHoldingItemUC

    @Inject
    lateinit var insertIntoHoldingsUC: InsertIntoHoldingsUC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUpdateHoldingItemBinding.inflate(inflater, container, false)
        val holdingModel = UpdateHoldingItemFragmentArgs.fromBundle(
            arguments
        ).cryptoHoldingModel
        binding.crypto = holdingModel

        val cryptoName = holdingModel.crypto_name

        binding.buttonUpdate.setOnClickListener {
            insertIntoHoldingsUC(arrayListOf(holdingModel.apply {
                holding_amount = binding.editHoldingAmount.text.toString().toDouble()
            })) {
                it.either({
                    close()
                    messageHandler.showError(getString(R.string.dialog_msg_update_failed, cryptoName))
                }, {
                    close()
                    messageHandler.showMessage(getString(R.string.dialog_msg_update_succeed, cryptoName))
                })
            }
        }

        binding.buttonUnwatch.setOnClickListener {
            deleteHoldingItemUC(DeleteHoldingItemUC.Params(holdingModel.portfolio_Id, holdingModel.crypto_id)) {
                it.either({
                    close()
                    messageHandler.showError(getString(R.string.dialog_msg_unwatch_failed, cryptoName))
                }, {
                    close()
                    messageHandler.showMessage(getString(R.string.dialog_msg_unwatch_succeed, cryptoName))
                })
            }
        }

        return binding.root
    }
}
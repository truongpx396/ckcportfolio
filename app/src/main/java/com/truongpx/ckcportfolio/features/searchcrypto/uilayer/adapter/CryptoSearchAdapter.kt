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

package com.truongpx.ckcportfolio.features.searchcrypto.uilayer.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel
import com.truongpx.ckcportfolio.features.searchcrypto.domainlayer.usecases.InsertIntoHoldingsUC
import com.truongpx.ckcportfolio.features.searchcrypto.uilayer.viewholder.CryptoSearchViewHolder
import javax.inject.Inject

class CryptoSearchAdapter @Inject constructor(
    insertIntoHoldingsUC: InsertIntoHoldingsUC,
    val portfolioManager: PortfolioManager
) :
    ListAdapter<CryptoInfoModel, CryptoSearchViewHolder>(
        CRYPTO_COMPARATOR
    ) {

    var addHoldingItemSuccessListener : (CryptoInfoModel)->Unit =  {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoSearchViewHolder {
        return CryptoSearchViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: CryptoSearchViewHolder, position: Int) {
        getItem(position)?.let {
            with(holder) {
                itemView.tag = it
                bindData(it, onItemClickListener)
            }
        }

    }

    private val onItemClickListener = View.OnClickListener {
        val itemData = it.tag as CryptoInfoModel
        val currentPortfolio = portfolioManager.getCurrentSelectedPortfolio()
        insertIntoHoldingsUC(
            arrayListOf(
                CryptoHoldingModel(
                    crypto_id = itemData.id,
                    crypto_name = itemData.name,
                    crypto_symbol = itemData.symbol,
                    portfolio_Id = currentPortfolio?.id ?: 0,
                    portfolio_name = currentPortfolio?.name ?: "",
                    logo = itemData.logo
                )
            )
        ) { eitherReceiver ->
            eitherReceiver.either({

            }, {
                addHoldingItemSuccessListener(itemData)
            })
        }

    }


    companion object {
        val CRYPTO_COMPARATOR = object : DiffUtil.ItemCallback<CryptoInfoModel>() {
            override fun areItemsTheSame(oldItem: CryptoInfoModel, newItem: CryptoInfoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CryptoInfoModel, newItem: CryptoInfoModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}
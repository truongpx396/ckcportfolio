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

package com.truongpx.ckcportfolio.features.basecrypto.uilayer.adapter

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.truongpx.ckcportfolio.MainDirections
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.viewholder.CryptoItemViewHolder
import javax.inject.Inject

class CryptoNormalAdapter @Inject constructor(
) :
    ListAdapter<CryptoHoldingModel, CryptoItemViewHolder>(
        CRYPTO_COMPARATOR
    ) {

    lateinit var clickListener: View.OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoItemViewHolder {
        return CryptoItemViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: CryptoItemViewHolder, position: Int) {
        getItem(position).let { cyptoModel ->
            with(holder) {
                itemView.tag = cyptoModel
                bind(cyptoModel, onItemClickListener())
            }
        }
    }

    fun onItemClickListener() = View.OnClickListener {
        val direction = MainDirections.actionToUpdatePortfolioFragment(it.tag as CryptoHoldingModel)
        it.findNavController().navigate(direction)
    }

    companion object {
        val PAYLOAD = Any()
        val CRYPTO_COMPARATOR = object : DiffUtil.ItemCallback<CryptoHoldingModel>() {
            override fun areItemsTheSame(oldItem: CryptoHoldingModel, newItem: CryptoHoldingModel): Boolean {
                return oldItem.crypto_name == newItem.crypto_name
            }

            override fun areContentsTheSame(oldItem: CryptoHoldingModel, newItem: CryptoHoldingModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
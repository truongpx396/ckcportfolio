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

package com.truongpx.ckcportfolio.features.basecrypto.uilayer.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.truongpx.ckcportfolio.databinding.ListItemCryptoBinding
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.corelibrary.extension.setUpAnimation


class CryptoItemViewHolder(private val binding: ListItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cryptoHoldingModel: CryptoHoldingModel?, clickListenerP: View.OnClickListener? = null) {
        binding.apply {
            root.setUpAnimation()
            crypto = cryptoHoldingModel
            clickListener = clickListenerP
            textPercentChange.bindTo(cryptoHoldingModel?.percent_change_24 ?: 0.0)
            executePendingBindings()
        }
    }

    companion object {
        fun newInstance(parent: ViewGroup): CryptoItemViewHolder {
            val binding = ListItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CryptoItemViewHolder(binding)
        }
    }
}
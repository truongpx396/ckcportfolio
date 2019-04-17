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

package com.truongpx.ckcportfolio.features.searchcrypto.uilayer.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.truongpx.ckcportfolio.core.extension.setUpAnimation
import com.truongpx.ckcportfolio.databinding.ListItemCryptoSearchBinding
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel

class CryptoSearchViewHolder(val binding: ListItemCryptoSearchBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: CryptoInfoModel, itemClickListener: View.OnClickListener) {
        binding.apply {
            itemModel = data
            clickListener = itemClickListener
            root.setUpAnimation()
        }
    }

    companion object {
        fun newInstance(parent: ViewGroup): CryptoSearchViewHolder {
            val binding = ListItemCryptoSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CryptoSearchViewHolder(binding)
        }
    }
}
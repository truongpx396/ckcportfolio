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

package com.truongpx.ckcportfolio.features.feednews.uilayer.adapter

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.truongpx.ckcportfolio.MainDirections
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.NetworkState
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.viewholder.NetworkStateItemViewHolder
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import com.truongpx.ckcportfolio.features.feednews.uilayer.viewholder.NewsItemViewHolder

class NewsAdapter(private val retry: () -> Unit = {}) :
    PagedListAdapter<NewsModel, RecyclerView.ViewHolder>(
        CRYPTO_COMPARATOR
    ) {

    private var networkState: NetworkState? = null

    private var onClickListener = View.OnClickListener {
        //NewsDetailFragmentArgs
        val directions= MainDirections.ActionToNewsDetailFragment(it.tag as NewsModel)
        it.findNavController().navigate(directions)
    }

    private val NETWORK_ITEM_VIEW = R.layout.item_network_state

    private val LIST_ITEM_CRYPTO_VIEW = R.layout.list_item_crypto

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            NETWORK_ITEM_VIEW -> NetworkStateItemViewHolder.newInstance(parent, retry)
            LIST_ITEM_CRYPTO_VIEW -> NewsItemViewHolder.newInstance(parent)
            else -> throw IllegalArgumentException("Illegal argument")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            NETWORK_ITEM_VIEW -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
            LIST_ITEM_CRYPTO_VIEW -> (holder as NewsItemViewHolder).apply {
                itemView.tag = getItem(position)
                bindTo(getItem(position), onClickListener)
            }
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_ITEM_VIEW
        } else LIST_ITEM_CRYPTO_VIEW
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val oldNetworkState = networkState
        networkState = newNetworkState
        if (oldNetworkState != newNetworkState) {
            when (newNetworkState) {
                NetworkState.LOADING -> notifyItemInserted(itemCount - 1)
                NetworkState.LOADED -> notifyItemRemoved(itemCount - 1)
            }

        }
    }

    companion object {
        val PAYLOAD = Any()
        val CRYPTO_COMPARATOR = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem.indexInResponse == newItem.indexInResponse
            }

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}
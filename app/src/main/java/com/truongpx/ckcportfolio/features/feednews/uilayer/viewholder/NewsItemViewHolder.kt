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

package com.truongpx.ckcportfolio.features.feednews.uilayer.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.truongpx.ckcportfolio.databinding.ListItemNewsBinding
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel

class NewsItemViewHolder(val binding: ListItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindTo(newsModel: NewsModel?, listener: View.OnClickListener) {
        newsModel?.let{
            binding.apply {
                news = it
                clickListener = listener
            }
        }

    }

    companion object {
        fun newInstance(parent: ViewGroup): NewsItemViewHolder {
            val itemBinding = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NewsItemViewHolder(itemBinding)
        }
    }
}
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

package com.truongpx.ckcportfolio.features.feednews.uilayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.core.exception.Failure
import com.truongpx.ckcportfolio.core.extension.close
import com.truongpx.ckcportfolio.core.extension.observe
import com.truongpx.ckcportfolio.core.extension.viewModel
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.core.platform.BaseFragment
import com.truongpx.ckcportfolio.databinding.FragmentNewsBinding
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.NetworkState
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import com.truongpx.ckcportfolio.features.feednews.domainlayer.usecase.InvalidateNewsUC
import com.truongpx.ckcportfolio.features.feednews.presentationlayer.viewmodel.NewsViewModel
import com.truongpx.ckcportfolio.features.feednews.uilayer.adapter.NewsAdapter
import javax.inject.Inject

class NewsFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsBinding

    private lateinit var newsViewModel: NewsViewModel

    private var needScrollToTop = false

    @Inject
    lateinit var invalidateNewsUC: InvalidateNewsUC

    private var newsAdapter = NewsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)


        newsViewModel = viewModel(viewModelFactory) {
            observe(newsPagedList, ::onReceivedNewsList)
            observe(networkState, ::onReceivedNetworkState)
            observe(failure, ::handlerFailure)
        }
        binding.listNews.adapter = newsAdapter

        binding.listNews.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        binding.swipeRefresh.apply {
            setOnRefreshListener {
                invalidateNewsUC(UseCase.None()) {
                    it.either({}, {

                    })
                }
//                newsViewModel.newsPagedList.value?.dataSource?.invalidate() ?: Any()
                isRefreshing = false
                needScrollToTop = true
            }
        }

        return binding.root
    }


    private fun onReceivedNewsList(newsList: PagedList<NewsModel>?) {
        newsAdapter.submitList(newsList)

        if (newsList?.isNotEmpty() == true && needScrollToTop) {
            binding.listNews.scrollToPosition(0)
            needScrollToTop = false
        }

    }

    private fun onReceivedNetworkState(networkState: NetworkState?) {
        newsAdapter.setNetworkState(networkState)
    }

    private fun handlerFailure(failure: Failure?) {
        failure?.let {
            when (it) {
                Failure.NetworkConnection -> {
                    messageHandler.showError(getString(R.string.failure_network_connection)); close()
                }
                Failure.ServerError -> {
                    messageHandler.showError(getString(R.string.failure_server_error));close()
                }
                else -> {
                    messageHandler.showError(getString(R.string.other_error)); close()
                }
            }
        }
    }
}
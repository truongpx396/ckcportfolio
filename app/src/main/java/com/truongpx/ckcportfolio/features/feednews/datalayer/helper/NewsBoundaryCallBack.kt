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

package com.truongpx.ckcportfolio.features.feednews.datalayer.helper

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.truongpx.corelibrary.utility.paginghelper.PagingRequestHelper
import com.truongpx.corelibrary.utility.paginghelper.createStatusLiveData
import com.truongpx.ckcportfolio.features.feednews.datalayer.database.NewsDatabase
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import com.truongpx.ckcportfolio.features.feednews.datalayer.network.NewsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class NewsBoundaryCallBack(
    private val newsApi: NewsApi, private val newsDatabase: NewsDatabase,
    private val ioExecutor: Executor
) : PagedList.BoundaryCallback<NewsModel>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()
    var startKey: Int = 1

    @MainThread
    override fun onZeroItemsLoaded() {
        startKey = 1
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            newsApi.getTopAfter(
                page = startKey
            ).enqueue(createWebServiceCallBack(it))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: NewsModel) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            newsApi.getTopAfter(page = startKey).enqueue(createWebServiceCallBack(it))
        }
    }

    private fun createWebServiceCallBack(it: PagingRequestHelper.Request.Callback): Callback<NewsApi.ListingResponse> {
        return object : Callback<NewsApi.ListingResponse> {

            override fun onFailure(call: Call<NewsApi.ListingResponse>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(call: Call<NewsApi.ListingResponse>, response: Response<NewsApi.ListingResponse>) {
                startKey += 1
                insertItemsIntoDb(response, it)
            }
        }
    }

    private fun insertItemsIntoDb(
        response: Response<NewsApi.ListingResponse>,
        it: PagingRequestHelper.Request.Callback
    ) {
        ioExecutor.execute {
            handlerResoponse(response.body())
            it.recordSuccess()
        }
    }

    private fun handlerResoponse(result: NewsApi.ListingResponse?) {
        result?.articles.let { news ->
            newsDatabase.runInTransaction {
                //                val start = newsDatabase.getNewsDao().getNextIndex()
//                val items = news?.mapIndexed { index, newsModel ->
//                    newsModel.indexInResponse = start + index
//                    newsModel
//                }
                val items = news?.map {
                    if (it.imageUrl == null) it.imageUrl = ""
                    if (it.content == null) it.content = ""
                    it
                }
                items?.let {
                    newsDatabase.getNewsDao().insert(it)
                }

            }
        }
    }


    fun getPageConfig(
        pageSize: Int,
        prefetchDistance: Int = pageSize,
        enablePlaceholders: Boolean = true,
        initialLoadSizeHint: Int =
            pageSize * 1,
        maxSize: Int = PagedList.Config.MAX_SIZE_UNBOUNDED
    ): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .setEnablePlaceholders(enablePlaceholders)
            .setInitialLoadSizeHint(initialLoadSizeHint)
            .setMaxSize(maxSize)
            .build()
    }


}
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

package com.truongpx.ckcportfolio.features.feednews.domainlayer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.toLiveData
import com.truongpx.ckcportfolio.core.exception.Failure
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.MyExecutorsService
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.Listing
import com.truongpx.ckcportfolio.features.feednews.datalayer.database.NewsDao
import com.truongpx.ckcportfolio.features.feednews.datalayer.database.NewsDatabase
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import com.truongpx.ckcportfolio.features.feednews.datalayer.helper.NewsBoundaryCallBack
import com.truongpx.ckcportfolio.features.feednews.datalayer.network.NewsApi
import javax.inject.Inject

interface NewsReposity {

    fun insertIntoNews(listNews: List<NewsModel>): Either<Failure, UseCase.None>

    fun getNews(): Either<Failure, DataSource.Factory<Int, NewsModel>>

    fun getNewsListing(): Either<Failure, LiveData<Listing<NewsModel>>>

    fun feedNews(page: Int): Either<Failure, List<NewsModel>>

    fun deleteAllNews(): Either<Failure, UseCase.None>

    class DefaultRespository @Inject constructor(
        val newsApi: NewsApi, val newsDao: NewsDao,
        val newsDatabase: NewsDatabase, val myExecutorsService: MyExecutorsService
    ) : NewsReposity {

        override fun insertIntoNews(listNews: List<NewsModel>): Either<Failure, UseCase.None> {
            return try {
                newsDao.insert(listNews)
                Either.rightDefault()
            } catch (ex: Exception) {
                Either.Left(Failure.DataBaseError)
            }
        }

        override fun getNews(): Either<Failure, DataSource.Factory<Int, NewsModel>> {
            return try {
                Either.Right(newsDao.getAllNews())
            } catch (ex: Exception) {
                Either.Left(Failure.DataBaseError)
            }
        }

        override fun feedNews(page: Int): Either<Failure, List<NewsModel>> {
            return try {
                Either.Right(newsApi.getTopAfter(page).execute().body()?.articles ?: listOf())
            } catch (ex: Exception) {
                Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getNewsListing(): Either<Failure, LiveData<Listing<NewsModel>>> {
            val boundaryCallback = NewsBoundaryCallBack(
                newsApi = newsApi,
                newsDatabase = newsDatabase,
                ioExecutor = myExecutorsService.DISK_IO
            )
            return try {
                val pageList =
                    newsDao.getAllNews()
                        .toLiveData(
                            boundaryCallback = boundaryCallback,
                            config = boundaryCallback.getPageConfig(NewsApi.NETWORK_PAGE_SIZE)
                        )
                val listing = MutableLiveData(
                    Listing(
                        pagedList = pageList,
                        networkState = boundaryCallback.networkState,
                        retry = {
                            boundaryCallback.helper.retryAllFailed()
                        },
                        refresh = {},
                        refreshState = boundaryCallback.networkState
                    )
                )
                Either.Right(listing)
            } catch (ex: Exception) {
                Either.leftDefault()
            }
        }

        override fun deleteAllNews(): Either<Failure, UseCase.None> {
            return try {
                newsDao.deleteAllNews()
                Either.rightDefault()
            } catch (ex: Exception) {
                Either.Left(Failure.DataBaseError)
            }
        }

    }
}
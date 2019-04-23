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

package com.truongpx.ckcportfolio.features.feednews.presentationlayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.truongpx.corelibrary.interactor.UseCase
import com.truongpx.corelibrary.platform.BaseViewModel
import com.truongpx.corelibrary.utility.NetworkState
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.Listing
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import com.truongpx.ckcportfolio.features.feednews.domainlayer.usecase.GetNewsListingUC
import javax.inject.Inject

class NewsViewModel @Inject constructor(getNewsListingUC: GetNewsListingUC) : BaseViewModel() {

    var newsPagedList = MediatorLiveData<PagedList<NewsModel>>()

    var networkState: LiveData<NetworkState> = MutableLiveData()

    init {
        getNewsListingUC(UseCase.None()) { it.either(::handleFailure, ::handleReceivedListing) }
    }

    private fun handleReceivedListing(listing: LiveData<Listing<NewsModel>>) {
        newsPagedList.addSource(listing) {
            networkState = Transformations.map(it.networkState) { state -> state }
            newsPagedList.addSource(Transformations.map(it.pagedList) { pagedList -> pagedList }) { pagedList ->
                newsPagedList.value = pagedList
            }
        }
    }


}
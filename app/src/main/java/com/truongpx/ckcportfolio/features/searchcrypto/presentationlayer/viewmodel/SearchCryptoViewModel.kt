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

package com.truongpx.ckcportfolio.features.searchcrypto.presentationlayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.truongpx.corelibrary.platform.BaseViewModel
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoModel
import com.truongpx.ckcportfolio.features.searchcrypto.domainlayer.usecases.GetCryptoIdsByNameUC
import com.truongpx.ckcportfolio.features.searchcrypto.domainlayer.usecases.GetSearchInfoUC
import javax.inject.Inject

class SearchCryptoViewModel @Inject constructor(
    val getCryptoIdsByNameUC: GetCryptoIdsByNameUC,
    val getSearchInfoUC: GetSearchInfoUC
) : BaseViewModel() {

    var searchResult = MediatorLiveData<List<CryptoInfoModel>>()


    fun searchCrypto(string: String) {
        if (string.isNotEmpty())
            getCryptoIdsByNameUC(string) {
                it.either(::handleFailure, ::handleListFromDataQuery)
            }
    }

    private fun handleListFromDataQuery(cryptoListName: LiveData<List<CryptoModel>>) {

        searchResult.addSource(cryptoListName) { listCryptoModel ->
            var ids = listCryptoModel.map { it.id.toString() }
            var arr = ids.joinToString(",")
            if (arr.isNotEmpty())
                getSearchInfoUC(arr) {
                    it.either(::handleFailure, ::postSearchResult)
                }
        }


    }

    private fun postSearchResult(searchResultP: LiveData<List<CryptoInfoModel>>) {
        searchResult.addSource(searchResultP) {
            searchResult.postValue(it)
        }
    }
}
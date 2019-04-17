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

package com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.core.platform.BaseViewModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.GetCryptoHoldingsUC
import javax.inject.Inject

class CryptoViewModel @Inject constructor(
    getCryptoHoldingsUC: GetCryptoHoldingsUC
) :
    BaseViewModel() {

    var holdingFull = MediatorLiveData<List<CryptoHoldingModel>>()

    init {
        getCryptoHoldingsUC(UseCase.None()) {
            it.either(::handleFailure, ::loadHolding)
        }
    }

    private fun loadHolding(holding: LiveData<List<CryptoHoldingModel>>) {
        holdingFull.addSource(holding, holdingFull::setValue)

    }

}
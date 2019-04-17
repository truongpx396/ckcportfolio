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

package com.truongpx.ckcportfolio.features.basecrypto.datalayer.workmanager

import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.CoinApi
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.InsertIntoCryptoUC
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.searchcrypto.domainlayer.usecases.InsertIntoHoldingsUC
import javax.inject.Inject

class WorkerData @Inject constructor(
    coinApi: CoinApi,
    portfolioManager: PortfolioManager,
    insertIntoCryptoUC: InsertIntoCryptoUC,
    insertIntoHoldingsUC: InsertIntoHoldingsUC
) {
    val mutableMap: MutableMap<String, Any> = mutableMapOf()

    companion object {
        const val COIN_API = "CoinApi"
        const val PORTFOLIO_MANAGER = "PortfolioManager"
        const val INSERTINTO_CRYPTO = "InsertIntoCryptoUC"
        const val INSERTINTO_HOLDING = "InsertIntoHoldingsUC"
    }

    init {
        mutableMap[COIN_API] = coinApi
        mutableMap[PORTFOLIO_MANAGER] = portfolioManager
        mutableMap[INSERTINTO_CRYPTO] = insertIntoCryptoUC
        mutableMap[INSERTINTO_HOLDING] = insertIntoHoldingsUC
    }
}
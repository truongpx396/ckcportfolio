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

import android.util.Log
import androidx.work.Worker
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.CoinApi
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.searchcrypto.domainlayer.usecases.InsertIntoHoldingsUC
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SeedDatabaseWorker @Inject constructor() : Worker() {
    private val TAG by lazy { SeedDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result {

        val mutableMap = MyWorkerManager.staticWorkerData.mutableMap

        val coinApi = mutableMap[WorkerData.COIN_API] as CoinApi
        val insertIntoHoldings = mutableMap[WorkerData.INSERTINTO_HOLDING] as InsertIntoHoldingsUC
        val portfolioManager = mutableMap[WorkerData.PORTFOLIO_MANAGER] as PortfolioManager

        return try {
            val listResult = coinApi.getCryptoInfoByIds("1,1027,52")
            listResult.enqueue(object : Callback<CoinApi.CryptoInfoResponse> {
                override fun onFailure(call: Call<CoinApi.CryptoInfoResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<CoinApi.CryptoInfoResponse>,
                    response: Response<CoinApi.CryptoInfoResponse>
                ) {
                    val mainPortfolio = portfolioManager.getPortfolioList()[0]
                    response.body()?.apply {
                        data.map { it.value }.forEach { cyptoInfoModel ->
                            insertIntoHoldings(
                                arrayListOf(
                                    CryptoHoldingModel(
                                        crypto_id = cyptoInfoModel.id,
                                        crypto_name = cyptoInfoModel.name,
                                        crypto_symbol = cyptoInfoModel.symbol,
                                        portfolio_Id = mainPortfolio.id,
                                        portfolio_name = mainPortfolio.name,
                                        logo = cyptoInfoModel.logo
                                    )
                                )
                            )
                        }
                    }

                }

            })

            Result.SUCCESS

        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.FAILURE
        }
    }
}

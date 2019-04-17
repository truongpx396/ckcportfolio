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

import androidx.work.Data
import androidx.work.Worker
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.CoinApi
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.InsertIntoCryptoUC
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SyncWorker : Worker() {

    // Define the parameter keys:

    companion object {
        val SYNC_TAG = "SYNC_WORKER_TAG "
    }


    private val KEY_X_ARG = "X"
    private val KEY_Y_ARG = "Y"
    private val KEY_Z_ARG = "Z"

    // The result key:
    private val KEY_RESULT = "result"

    /**
     * This will be called whenever work manager run the work.
     */
    override fun doWork(): Result {
        // Fetch the arguments (and specify default values):
        val x = inputData.getLong(KEY_X_ARG, 0)
        val y = inputData.getLong(KEY_Y_ARG, 0)
        val z = inputData.getLong(KEY_Z_ARG, 0)

        val mutableMap = MyWorkerManager.staticWorkerData.mutableMap

        val coinApi = mutableMap[WorkerData.COIN_API] as CoinApi
        val insertIntoCrypto = mutableMap[WorkerData.INSERTINTO_CRYPTO] as InsertIntoCryptoUC

        val timeToSleep = x + y + z
//        Thread.sleep(timeToSleep)

        coinApi.getAllCoins().enqueue(object : Callback<CoinApi.ListingResponse> {

            override fun onFailure(call: Call<CoinApi.ListingResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<CoinApi.ListingResponse>, response: Response<CoinApi.ListingResponse>) {
                insertIntoCrypto(response.body()!!.data)
            }
        })


        //...set the output, and we're done!
        val outputData1 = Data.Builder()
            .putInt(KEY_RESULT, timeToSleep.toInt())
            .build()

        outputData = outputData1
        // Indicate success or failure with your return value.
        return Result.SUCCESS
    }
}
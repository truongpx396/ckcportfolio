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

import androidx.work.*
import com.truongpx.corelibrary.di.ApplicationComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MyWorkerManager(val applicationComponent: ApplicationComponent) {

    @Inject
    lateinit var mWorkerData: WorkerData

    companion object {
        val DATABASE_KEY = "data"
        val API_KEY = "api"
        lateinit var staticWorkerData: WorkerData
    }

    fun launchWorker() {
        applicationComponent.inject(this)
        staticWorkerData = mWorkerData

        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

//        val mapdata: MutableMap<String, Any> = mutableMapOf()
//        mapdata[API_KEY] = coinApi

        //val dataBuilder = Data.Builder().putAll(staticWorkerData.mutableMap)

        val request = PeriodicWorkRequest
            .Builder(SyncWorker::class.java, 15, TimeUnit.MINUTES).addTag(
                SyncWorker.SYNC_TAG
            )
         //   .setInputData(dataBuilder.build())
            .setConstraints(myConstraints)
            .build()

        WorkManager.getInstance()?.enqueueUniquePeriodicWork(
            SyncWorker.SYNC_TAG,
            ExistingPeriodicWorkPolicy.REPLACE, request
        )

    }

}
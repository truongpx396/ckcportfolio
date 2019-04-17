/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongpx.ckcportfolio.core.di

import android.content.Context
import com.google.gson.Gson
import com.truongpx.ckcportfolio.AndroidApplication
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.MyExecutorsService
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.MySharedPreferences
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.CoinApi
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.DeleteHoldingItemByPortfolioIdUC
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.feednews.datalayer.network.NewsApi
import com.truongpx.ckcportfolio.features.feednews.domainlayer.repository.NewsReposity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application


    @Provides
    @Singleton
    fun provideCoinApi(): CoinApi {
        return CoinApi.newInstance()
    }

    @Provides
    @Singleton
    fun provideNewsApi(context: Context): NewsApi {
        return NewsApi.newInstance(context)
    }

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideExecutorService() = MyExecutorsService()

    @Provides
    @Singleton
    fun provideSharePreferences() = MySharedPreferences(
        context = provideApplicationContext(),
        gson = provideGson()
    )

    @Provides
    @Singleton
    fun providePortfolioManager(
        mySharedPreferences: MySharedPreferences,
        deleteHoldingItemByPortfolioIdUC: DeleteHoldingItemByPortfolioIdUC
    ) = PortfolioManager(
        mySharedPreferences,
        deleteHoldingItemByPortfolioIdUC
    )

    @Singleton
    @Provides
    fun cryptoRepository(dataSource: CryptoRepository.DefaultRepository): CryptoRepository = dataSource

    @Singleton
    @Provides
    fun newsRepository(dataSource: NewsReposity.DefaultRespository): NewsReposity = dataSource
//
}

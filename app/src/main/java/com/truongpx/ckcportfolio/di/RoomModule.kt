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

package com.truongpx.corelibrary.di

import android.app.Application
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoDao
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoDatabase
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoHoldingsDao
import com.truongpx.ckcportfolio.features.feednews.datalayer.database.NewsDao
import com.truongpx.ckcportfolio.features.feednews.datalayer.database.NewsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(mApplication: Application) {

    private val cryptoDatabase by lazy {
        CryptoDatabase.create(mApplication.applicationContext, false)
    }

    private val newsDatabase by lazy {
        NewsDatabase.create(mApplication.applicationContext, false)
    }

    @Singleton
    @Provides
    fun providesCryptoDatabase() = cryptoDatabase


    @Singleton
    @Provides
    fun provideCryptoDao(cryptoDatabase: CryptoDatabase): CryptoDao {
        return cryptoDatabase.getCryptoDao()
    }

    @Singleton
    @Provides
    fun provideCryptoHoldingsDao(cryptoDatabase: CryptoDatabase): CryptoHoldingsDao {
        return cryptoDatabase.getCryptoHoldingsDao()
    }

    @Singleton
    @Provides
    fun providesNewsDatabase() = newsDatabase

    @Singleton
    @Provides
    fun providesNewsDao(newsDatabase: NewsDatabase): NewsDao {
        return newsDatabase.getNewsDao()
    }

}
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
package com.truongpx.corelibrary.di


import com.truongpx.ckcportfolio.AndroidApplication
import com.truongpx.corelibrary.di.viewmodel.ViewModelModule
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.workmanager.MyWorkerManager
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.CryptoFragment
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.PagerItemPorfolioFragment
import com.truongpx.ckcportfolio.features.createportfolio.uilayer.CreatePortfolioFragment
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.UpdateHoldingItemFragment
import com.truongpx.ckcportfolio.features.feednews.uilayer.NewsFragment
import com.truongpx.ckcportfolio.features.searchcrypto.uilayer.SearchCryptoFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class, RoomModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)

    fun inject(workManager: MyWorkerManager)

    fun inject(cryptoFragment: CryptoFragment)

    fun inject(searchCryptoFragment: SearchCryptoFragment)

    fun inject(newsFeedFragment: NewsFragment)

    fun inject(createPorfolioFragment: CreatePortfolioFragment)

    fun inject(pagerItemPorfolioFragment: PagerItemPorfolioFragment)

    fun inject(updateHoldingItemFragment: UpdateHoldingItemFragment)


//    fun inject(routeActivity: RouteActivity)
//
//    fun inject(moviesFragment: MoviesFragment)


}

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
package com.truongpx.corelibrary.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel.CryptoViewModel
import com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel.PagerHoldingViewModel
import com.truongpx.ckcportfolio.features.feednews.presentationlayer.viewmodel.NewsViewModel
import com.truongpx.ckcportfolio.features.searchcrypto.presentationlayer.viewmodel.SearchCryptoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CryptoViewModel::class)
    abstract fun bindCryptoViewModel(cryptoViewModel: CryptoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchCryptoViewModel::class)
    abstract fun bindSearchCryptoViewModel(searchCryptoViewModel: SearchCryptoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(searchCryptoViewModel: NewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PagerHoldingViewModel::class)
    abstract fun bindPagerHoldingViewModel(pagerHoldingViewModel: PagerHoldingViewModel): ViewModel


//    @Binds
//    @IntoMap
//    @ViewModelKey(MoviesViewModel::class)
//    abstract fun bindsMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel


}
package com.truongpx.ckcportfolio.features.feednews

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.*
import com.truongpx.ckcportfolio.AndroidTest
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.NetworkState
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.Listing
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import com.truongpx.ckcportfolio.features.feednews.domainlayer.usecase.GetNewsListingUC
import com.truongpx.ckcportfolio.features.feednews.presentationlayer.viewmodel.NewsViewModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito

class NewsViewModelTest : AndroidTest() {

    private lateinit var newsViewModel: NewsViewModel


    var networkStateLive = MutableLiveData<NetworkState>().also { it.value = NetworkState.LOADED }

    private var pageList = mockPagedList(emptyList<NewsModel>())


    private var pagedListNews = MutableLiveData<PagedList<NewsModel>>().also { it.value = pageList }

    private var listingResult =
        Listing(pagedList = pagedListNews,
            networkState = networkStateLive,
            refreshState = networkStateLive,
            refresh = {},
            retry = {})

    private var data = Either.Right(MutableLiveData<Listing<NewsModel>>().also { it.value = listingResult })


    @Mock
    private lateinit var getNewsListingUC: GetNewsListingUC

    @Before
    fun setUp() {
        given { runBlocking { getNewsListingUC.run(any()) } }.willReturn(data)
    }

    @Test
    fun `should get data from NewsViewModel when init`() {
        newsViewModel =
            NewsViewModel(getNewsListingUC)

        newsViewModel.newsPagedList.observeForever {
            it shouldEqual pageList
        }

//        verifyBlocking(getNewsListingUC,run(any()))

//        verifyNoMoreInteractions(getNewsListingUC)
    }

    private fun <T> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        Mockito.`when`(pagedList[ArgumentMatchers.anyInt()]).then { invocation ->
            val index = invocation.arguments.first() as Int
            list[index]
        }
        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}
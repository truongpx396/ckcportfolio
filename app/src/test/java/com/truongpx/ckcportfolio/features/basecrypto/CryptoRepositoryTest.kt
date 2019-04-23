package com.truongpx.ckcportfolio.features.basecrypto

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.truongpx.ckcportfolio.UnitTest
import com.truongpx.corelibrary.exception.Failure
import com.truongpx.corelibrary.functional.Either
import com.truongpx.corelibrary.platform.NetworkHandler
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoDao
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoHoldingsDao
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.CoinApi
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeInstanceOf

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class CryptoRepositoryTest : UnitTest() {
    private lateinit var cryptoRepository: CryptoRepository

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var coinApi: CoinApi
    @Mock
    private lateinit var cryptoDao: CryptoDao
    @Mock
    private lateinit var cryptoHoldingsDao: CryptoHoldingsDao

    private var searchResultTestInstance = CryptoInfoModel.getEmptyInstance()

    private var listSearchResult: List<CryptoInfoModel> = listOf(searchResultTestInstance)

    private var dataSearchResult: Map<String, CryptoInfoModel> = mapOf(Pair("1", searchResultTestInstance))


    @Mock
    private lateinit var cryptoInfo: CoinApi.CryptoInfoResponse

    @Mock
    private lateinit var responseCryptoInfo: Response<CoinApi.CryptoInfoResponse>

    @Mock
    private lateinit var callCryptoInfo: Call<CoinApi.CryptoInfoResponse>

    @Before
    fun setUp() {
        cryptoRepository = CryptoRepository.DefaultRepository(networkHandler, coinApi, cryptoDao, cryptoHoldingsDao)
    }


    @Test
    fun `should return empty list when search query has no data`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { responseCryptoInfo.body() }.willReturn(null)
        given { callCryptoInfo.execute() }.willReturn(responseCryptoInfo)
        given { coinApi.getCryptoInfoByIds("") }.willReturn(callCryptoInfo)

        val searchResult = cryptoRepository.getSearchInfo("")

        if (searchResult is Either.Right) {
            searchResult.b.value shouldEqual Either.Right(MutableLiveData(emptyList<CryptoInfoModel>())).b.value
        }

        verify(coinApi).getCryptoInfoByIds("")
    }

    @Test
    fun `should return result list when search query has  data`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { cryptoInfo.data }.willReturn(dataSearchResult)
        given { responseCryptoInfo.body() }.willReturn(cryptoInfo)
        given { callCryptoInfo.execute() }.willReturn(responseCryptoInfo)
        given { coinApi.getCryptoInfoByIds("") }.willReturn(callCryptoInfo)

        val searchResult = cryptoRepository.getSearchInfo("")

        if (searchResult is Either.Right) {
            searchResult.b.value shouldEqual Either.Right(MutableLiveData(listSearchResult)).b.value
        }

        verify(coinApi).getCryptoInfoByIds("")
    }

    @Test
    fun `should return network failure when search query has no network connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val searchResult = cryptoRepository.getSearchInfo("")

        searchResult shouldBeInstanceOf Either::class.java
        searchResult.isLeft shouldBe true
        searchResult.either({ failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java }, {})

        verifyZeroInteractions(coinApi)
    }


}
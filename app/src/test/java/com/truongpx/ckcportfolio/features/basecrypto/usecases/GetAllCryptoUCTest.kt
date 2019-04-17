package com.truongpx.ckcportfolio.features.basecrypto.usecases

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.truongpx.ckcportfolio.UnitTest
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoModel
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.GetAllCryptoUC
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetAllCryptoUCTest : UnitTest() {

    private lateinit var getAllCryptoUCTest: GetAllCryptoUC
    @Mock
    private lateinit var cryptoRepository: CryptoRepository

    @Before
    fun setUp() {
        getAllCryptoUCTest =
            GetAllCryptoUC(cryptoRepository)
        given { cryptoRepository.getAllCrypto() }.willReturn(Either.Right(MutableLiveData<List<CryptoModel>>(listOf())))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getAllCryptoUCTest.run(UseCase.None()) }
        verify(cryptoRepository).getAllCrypto()
        verifyNoMoreInteractions(cryptoRepository)
    }
}
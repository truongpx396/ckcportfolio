package com.truongpx.ckcportfolio.features.basecrypto.usecases

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.truongpx.ckcportfolio.UnitTest
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.GetCryptoHoldingsUC
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCryptoHoldingsUCTest : UnitTest() {

    private lateinit var getCryptoHoldingsUC: GetCryptoHoldingsUC
    @Mock
    private lateinit var cryptoRepository: CryptoRepository

    @Before
    fun setUp() {
        getCryptoHoldingsUC =
            GetCryptoHoldingsUC(cryptoRepository)
        given { cryptoRepository.getCryptoHoldings() }.willReturn(
            Either.Right(
                MutableLiveData<List<CryptoHoldingModel>>(
                    listOf()
                )
            )
        )
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getCryptoHoldingsUC.run(UseCase.None()) }
        verify(cryptoRepository).getCryptoHoldings()
        verifyNoMoreInteractions(cryptoRepository)
    }
}
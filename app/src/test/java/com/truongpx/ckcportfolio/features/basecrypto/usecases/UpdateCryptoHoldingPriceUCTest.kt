package com.truongpx.ckcportfolio.features.basecrypto.usecases

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.truongpx.ckcportfolio.UnitTest
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.core.interactor.UseCase
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.UpdateCryptoHoldingPriceUC
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class UpdateCryptoHoldingPriceUCTest : UnitTest() {

    private lateinit var updateCryptoHoldingPriceUC: UpdateCryptoHoldingPriceUC

    @Mock
    lateinit var cryptoRepository: CryptoRepository

    @Before
    fun setUp() {
        updateCryptoHoldingPriceUC =
            UpdateCryptoHoldingPriceUC(
                cryptoRepository
            )
        given { cryptoRepository.updateCryptoHoldingPrice() }.willReturn(Either.rightDefault())
    }

    @Test
    fun `should call update data to repository`() {
        runBlocking { updateCryptoHoldingPriceUC.run(UseCase.None()) }
        verify(cryptoRepository).updateCryptoHoldingPrice()
        verifyNoMoreInteractions(cryptoRepository)
    }
}
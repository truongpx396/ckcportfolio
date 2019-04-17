package com.truongpx.ckcportfolio.features.basecrypto.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.truongpx.ckcportfolio.UnitTest
import com.truongpx.ckcportfolio.core.functional.Either
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.DeleteHoldingItemUC
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DeleteHoldingItemUCTest : UnitTest() {

    private lateinit var deleteHoldingItemUC: DeleteHoldingItemUC

    @Mock
    private lateinit var cryptoRepository: CryptoRepository

    @Before
    fun setUp() {
        deleteHoldingItemUC =
            DeleteHoldingItemUC(cryptoRepository)
        given { cryptoRepository.deleteHoldingItem(any(), any()) }.willReturn(Either.rightDefault())
    }

    @Test
    fun `should delete data from repository`() {
        runBlocking { deleteHoldingItemUC.run(DeleteHoldingItemUC.Params(0, 0)) }
        verify(cryptoRepository).deleteHoldingItem(any(), any())
        verifyNoMoreInteractions(cryptoRepository)
    }
}
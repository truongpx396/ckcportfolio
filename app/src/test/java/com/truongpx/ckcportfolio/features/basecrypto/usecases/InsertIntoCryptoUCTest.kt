package com.truongpx.ckcportfolio.features.basecrypto.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.truongpx.ckcportfolio.UnitTest
import com.truongpx.corelibrary.functional.Either
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository.CryptoRepository
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.InsertIntoCryptoUC
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class InsertIntoCryptoUCTest : UnitTest() {

    private lateinit var insertIntoCryptoUC: InsertIntoCryptoUC
    @Mock
    private lateinit var cryptoRepository: CryptoRepository

    @Before
    fun setUp() {
        insertIntoCryptoUC =
            InsertIntoCryptoUC(cryptoRepository)
        given { cryptoRepository.insertIntoCrypto(any()) }.willReturn(Either.rightDefault())
    }

    @Test
    fun `should insert data to repository`() {
        runBlocking { insertIntoCryptoUC.run(listOf()) }
        verify(cryptoRepository).insertIntoCrypto(any())
        verifyNoMoreInteractions(cryptoRepository)
    }
}
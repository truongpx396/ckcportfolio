package com.truongpx.ckcportfolio.features.basecrypto

import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.truongpx.ckcportfolio.AndroidTest
import com.truongpx.corelibrary.functional.Either
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.GetCryptoHoldingsUC
import com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel.CryptoViewModel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CryptoViewModelTest : AndroidTest() {

    private lateinit var cryptoViewModel: CryptoViewModel

    @Mock
    private lateinit var getCryptoHoldingsUC: GetCryptoHoldingsUC

    private var cryptoHoldingModel = CryptoHoldingModel.getEmptyInstance()

    @Before
    fun setUp() {
        given { runBlocking { getCryptoHoldingsUC.run(any()) } }.willReturn(
            Either.Right(
                MutableLiveData<List<CryptoHoldingModel>>(
                    listOf(cryptoHoldingModel)
                )
            )
        )
    }

    @Test
    fun `cryptoViewModel should load data when init`() {
        cryptoViewModel = CryptoViewModel(
            getCryptoHoldingsUC
        )
        cryptoViewModel.holdingFull.observeForever {
            it.size shouldEqual 1
            it[0] shouldEqual cryptoHoldingModel
        }
    }
}
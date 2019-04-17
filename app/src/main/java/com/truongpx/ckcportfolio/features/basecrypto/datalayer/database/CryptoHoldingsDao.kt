/*
 *
 *   Copyright (C) 2019 Truongpx Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.truongpx.ckcportfolio.features.basecrypto.datalayer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingInfo
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel

@Dao
interface CryptoHoldingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cryptoHolding: List<CryptoHoldingModel>)

    @Query("SELECT * FROM crypto_holding WHERE portfolio_id=:portfolioId")
    fun getHoldingById(portfolioId: Int): LiveData<List<CryptoHoldingModel>>

    @Query("SELECT * FROM crypto_holding")
    fun getHoldings(): LiveData<List<CryptoHoldingModel>>

    @Query("SELECT * FROM crypto_holding")
    fun getHoldings1(): List<CryptoHoldingModel>

    @Query("DELETE FROM crypto_holding WHERE portfolio_id=:portfolioId")
    fun deleteHolding(portfolioId: Int)

    @Query("DELETE FROM crypto_holding WHERE portfolio_id=:portfolioId and crypto_id=:cryptoId")
    fun deleteHoldingItem(portfolioId: Int,cryptoId: Int)


//    @Transaction
//    @Query("SELECT * FROM CRYPTO ")
//    fun getCryptoHoldingInfo() : LiveData<List<CryptoHoldingInfo>>

    @Transaction
    @Query("SELECT * FROM CRYPTO_HOLDING ")
    fun getCryptoHoldingInfo(): LiveData<List<CryptoHoldingInfo>>

//    @Transaction
//    fun getHoldingInfoById(holdingId :Int){
//        var holdingInfoList = getCryptoHoldingInfo()
//        holdingInfoList.value?.filter { it.cryptoHoldings.filter { it.portfolio_Id==holdingId } ;true }
//    }

}
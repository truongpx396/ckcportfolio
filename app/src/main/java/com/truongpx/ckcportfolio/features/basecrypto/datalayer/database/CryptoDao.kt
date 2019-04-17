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
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoModel


@Dao
interface CryptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cryptos : List<CryptoModel>)

    @Query("SELECT MAX(indexInResponse) + 1  FROM crypto")
    fun getNextIndex() :Int

    @Query("SELECT * FROM crypto ORDER BY indexInResponse ASC")
    fun getAllCrypto(): DataSource.Factory<Int, CryptoModel>

    @Query("Select * from crypto")
    fun getAllCryptoNormal(): LiveData<List<CryptoModel>>

    @Query("Select * from crypto where name like :nameCrypto or symbol like :nameCrypto ")
    fun getCryptoByName(nameCrypto: String) : LiveData<List<CryptoModel>>

    @Query("Select * from crypto where name like :firstLetter or symbol like :firstLetter ")
    fun getCryptoByFirstLetter(firstLetter: String) : LiveData<List<CryptoModel>>
}
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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.workmanager.SeedDatabaseWorker

@Database(entities = [CryptoModel::class, CryptoHoldingModel::class], version = 9, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context, useInmemory: Boolean): CryptoDatabase {
            val databaseBuilder = if (useInmemory) {
                Room.inMemoryDatabaseBuilder(context, CryptoDatabase::class.java)
            } else {
                Room.databaseBuilder(context, CryptoDatabase::class.java, "crypto.db")
            }
            databaseBuilder.addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val myConstraints = Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().setConstraints(myConstraints).build()
                    WorkManager.getInstance()?.enqueue(request)
                }
            })
            return databaseBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun getCryptoDao(): CryptoDao

    abstract fun getCryptoHoldingsDao(): CryptoHoldingsDao
}
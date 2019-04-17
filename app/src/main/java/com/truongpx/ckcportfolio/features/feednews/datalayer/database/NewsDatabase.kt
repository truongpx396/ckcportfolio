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

package com.truongpx.ckcportfolio.features.feednews.datalayer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel

@Database(entities = [NewsModel::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context, useInmemory: Boolean): NewsDatabase {
            val databaseBuilder = if (useInmemory) {
                Room.inMemoryDatabaseBuilder(context, NewsDatabase::class.java)
            } else {
                Room.databaseBuilder(context, NewsDatabase::class.java, "news.db")
            }
            return databaseBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun getNewsDao(): NewsDao
}
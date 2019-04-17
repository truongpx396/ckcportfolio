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

import androidx.paging.DataSource
import androidx.room.*
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listNews: List<NewsModel>?)

    @Query("SELECT MAX(indexInResponse)+1 FROM news")
    fun getNextIndex(): Int

    @Query("SELECT * FROM news ORDER BY publishedAt DESC")
    fun getAllNews(): DataSource.Factory<Int, NewsModel>

    @Query("DELETE FROM news")
    fun deleteAllNews()
}
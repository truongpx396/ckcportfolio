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

package com.truongpx.ckcportfolio.features.feednews.datalayer.entities

import android.os.Parcel
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.truongpx.corelibrary.platform.KParcelable
import com.truongpx.corelibrary.platform.parcelableCreator
import com.truongpx.corelibrary.platform.readDate
import com.truongpx.corelibrary.platform.writeDate
import java.util.*

@Entity(tableName = "news")
class NewsModel(
    @PrimaryKey
    @SerializedName("publishedAt")
    val publishedAt: Date?,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @Nullable
    @SerializedName("urlToImage")
    var imageUrl: String,
    @SerializedName("content")
    var content: String
) : KParcelable {
    var indexInResponse: Int = -1
    val publishedTimeText: String
        get() {
            val timeSpace =
                Calendar.getInstance().timeInMillis - Calendar.getInstance().apply { time = publishedAt }.timeInMillis
            val secondTime = 1000 * 60 - 1
            val minuteTime = 1000 * 60 * 60 - 1
            val hourTime = 1000 * 60 * 60 * 24 - 1
            return when (timeSpace) {
                in 0..999 -> "Now"
                in 1000..secondTime -> (timeSpace / 1000).toString() + " seconds Ago"
                in secondTime + 1..minuteTime -> (timeSpace / 1000 / 60).toString() + " minutes Ago"
                in minuteTime + 1..hourTime -> (timeSpace / 1000 / 60 / 60).toString() + " hours Ago"
                else -> (timeSpace / 1000 / 60 / 60 / 24).toString() + " days Ago"
            }
        }

    constructor(parcel: Parcel) : this(
        parcel.readDate(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        indexInResponse = parcel.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeDate(publishedAt)
            writeString(url)
            writeString(title)
            writeString(imageUrl)
            writeString(content)
            writeInt(indexInResponse)
        }

    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::NewsModel)

        fun getEmptyInstance() = NewsModel(
            publishedAt = null,
            url = "",
            title = "",
            imageUrl = "",
            content = ""
        )
    }

}
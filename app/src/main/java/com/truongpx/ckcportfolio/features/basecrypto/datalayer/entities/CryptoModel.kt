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

package com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "crypto")
data class CryptoModel(
    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("symbol")
    var symbol: String = "",
    @SerializedName("slug")
    var slug: String = "",
    @SerializedName("circulating_supply")
    var circulating_supply: Double = 0.0,
    @SerializedName("total_supply")
    var total_supply: Double = 0.0,
    @SerializedName("max_supply")
    var max_supply: Double = 0.0,
    @SerializedName("num_market_pairs")
    var num_market_pairs: Int = 0,
    @SerializedName("cmc_rank")
    var cmc_rank: Int = 0,
    @Embedded
    @SerializedName("quote")
    var quote: QuoteContainer? = null

//    @Embedded
//    var quoteModel: QuoteCryptoModel
) {

    var indexInResponse: Int = -1

    var imgUrl: String = "http"

    val rankString: String
        get() {
            return cmc_rank.toString()
        }
}

data class QuoteContainer(@Embedded val USD: QuoteCryptoModel)
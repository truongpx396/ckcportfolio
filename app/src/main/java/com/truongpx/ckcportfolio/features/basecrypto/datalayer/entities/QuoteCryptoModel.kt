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

import com.google.gson.annotations.SerializedName

data class QuoteCryptoModel(
    val crypto_id: Int,
    //val USD: QuoteContent
    @SerializedName("price")
    val price: Double,
    @SerializedName("volume_24h")
    val volume_24h: Double,
    @SerializedName("percent_change_1h")
    val percent_change_1h: Double,
    @SerializedName("percent_change_24h")
    val percent_change_24h: Double,
    @SerializedName("percent_change_7d")
    val percent_change_7d: Double,
    @SerializedName("market_cap")
    val market_cap: Double
) {
    val PriceString: String
        get() = price.toString()
}

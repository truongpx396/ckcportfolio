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

import android.os.Parcel
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.truongpx.ckcportfolio.core.extension.format
import com.truongpx.ckcportfolio.core.platform.KParcelable
import com.truongpx.ckcportfolio.core.platform.parcelableCreator

@Entity(tableName = "crypto_holding", primaryKeys = ["crypto_id", "portfolio_id"])
data class CryptoHoldingModel(
    @ColumnInfo(name = "crypto_id")
    val crypto_id: Int,
    @ColumnInfo(name = "crypto_name")
    val crypto_name: String,
    @ColumnInfo(name = "crypto_symbol")
    val crypto_symbol: String,
    @ColumnInfo(name = "portfolio_id")
    val portfolio_Id: Int,
    @ColumnInfo(name = "portfolio_name")
    val portfolio_name: String,
    @ColumnInfo(name = "logo_url")
    val logo: String,
    @ColumnInfo(name = "current_price")
    var current_price: Double = 0.0,
    @ColumnInfo(name = "holding_amout")
    var holding_amount: Double = 0.0,
    @ColumnInfo(name = "percent_change_24h")
    var percent_change_24: Double = 0.0

) : KParcelable {


    val portfolioId: String
        get() = portfolio_Id.toString()
    val currentPrice: String
        get() {
            return if (current_price < 1.0)
                "\$${current_price.format(7)}"
            else "\$${current_price.format(2)}"
        }
    val holdingAmount: String
        get() = holding_amount.toString()

    val holdingValue: Double
        get() = holding_amount * current_price

    val holdingValueText: String
        get() {
            var value = holdingValue
            return if (value < 1.0)
                "\$${value.format(7)}"
            else "\$${value.format(2)}"
        }
    val percentChange: String
        get() {
            return if (percent_change_24 >= 0)
                "+${percent_change_24.format(2)}%"
            else "${percent_change_24.format(2)}%"
        }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(crypto_id)
            writeString(crypto_name)
            writeString(crypto_symbol)
            writeInt(portfolio_Id)
            writeString(portfolio_name)
            writeString(logo)
            writeDouble(current_price)
            writeDouble(holding_amount)
            writeDouble(percent_change_24)
        }

    }

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::CryptoHoldingModel)

        fun getEmptyInstance() = CryptoHoldingModel(
            crypto_id = 0,
            crypto_name = "",
            crypto_symbol = "",
            portfolio_Id = 0,
            portfolio_name = "",
            logo = "",
            current_price = 0.0,
            holding_amount = 0.0,
            percent_change_24 = 0.0
        )
    }
}
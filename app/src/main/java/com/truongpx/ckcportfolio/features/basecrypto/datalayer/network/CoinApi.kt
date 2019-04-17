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

package com.truongpx.ckcportfolio.features.basecrypto.datalayer.network

import android.util.Log
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoModel
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinApi {

    @Headers(HEADER)
    @GET("/v1/cryptocurrency/listings/latest?limit=5000")
    fun getAllCoins(): Call<ListingResponse>

    @Headers(HEADER)
    @GET("v1/cryptocurrency/quotes/latest")
    fun getQuoteCoins(@Query("id") ids: String): Call<QuoteResponse>

    @Headers(HEADER)
    @GET("/v1/cryptocurrency/listings/latest")
    fun getTop(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int
    ): Call<ListingResponse>


    @Headers(HEADER)
    @GET("/v1/cryptocurrency/listings/latest")
    fun getTopAfter(
        @Query("start") start: Int,
        @Query("limit") limit: Int
    ): Call<ListingResponse>


    @Headers(HEADER)
    @GET("/v1/cryptocurrency/info")
    fun getCryptoInfoByIds(
        @Query("id") id: String
    ): Call<CryptoInfoResponse>


    class ListingResponse(val data: List<CryptoModel>)

    class QuoteResponse(val data: Map<String, CryptoModel>)

    class CryptoInfoResponse(val data: Map<String, CryptoInfoModel>)


    companion object {
        private const val BASE_URL = "https://sandbox-api.coinmarketcap.com/"
        const val API_KEY = "e0484769-c1c4-4523-8aee-e01328ff5c88"
        const val HEADER: String = "X-CMC_PRO_API_KEY: $API_KEY"
        fun newInstance(): CoinApi =
            newInstance(
                HttpUrl.parse(BASE_URL)!!
            )
        private fun newInstance(httpUrl: HttpUrl): CoinApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CoinApi::class.java)
        }
    }
}
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

package com.truongpx.ckcportfolio.features.feednews.datalayer.network

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.truongpx.ckcportfolio.features.feednews.datalayer.entities.NewsModel
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*


interface NewsApi {

    @Headers(HEADER)
    @GET("/v2/everything")
    fun getTopAfter(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = NETWORK_PAGE_SIZE,
        @Query("sources") sources: String = "crypto-coins-news"
    ): Call<ListingResponse>

    class ListingResponse(val articles: List<NewsModel>)

    companion object {
        const val NETWORK_PAGE_SIZE = 10
        private const val BASE_URL = "https://newsapi.org/"
        private const val API_KEY = "00bdfe9eec944f439bb1fad6a743de46"
        const val HEADER: String = "x-api-key: $API_KEY"
        fun newInstance(context: Context): NewsApi =
            newInstance(
                context,
                HttpUrl.parse(BASE_URL)!!
            )
        private fun newInstance(context: Context, httpUrl: HttpUrl): NewsApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val gson = GsonBuilder().registerTypeAdapter(Calendar::class.java,
                timeStamp
            )
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create()

            val client =
                OkHttpClient.Builder().sslSocketFactory(
                    SSLConnectHelper.getSSLContext(
                        context
                    ).socketFactory,
                    SSLConnectHelper.getX509TrustManager()
                )
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(NewsApi::class.java)
        }

//        private val timeStamp = JsonDeserializer<Calendar> { json, _, _ ->
////            Calendar.getInstance().apply {
////                time=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(json.asString)
////            }
////        }

        private val timeStamp = JsonDeserializer<Date> { json, _, _ ->
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(json.asString)
        }
    }

}
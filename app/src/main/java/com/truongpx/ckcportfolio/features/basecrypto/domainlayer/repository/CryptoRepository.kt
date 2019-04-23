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

package com.truongpx.ckcportfolio.features.basecrypto.domainlayer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.truongpx.corelibrary.exception.Failure
import com.truongpx.corelibrary.functional.Either
import com.truongpx.corelibrary.interactor.UseCase
import com.truongpx.corelibrary.platform.NetworkHandler
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoDao
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.database.CryptoHoldingsDao
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.network.CoinApi
import com.truongpx.ckcportfolio.features.searchcrypto.datalayer.entities.CryptoInfoModel
import javax.inject.Inject

interface CryptoRepository {


    fun getAllCrypto(): Either<Failure, LiveData<List<CryptoModel>>>

    fun getCryptoHoldings(): Either<Failure, LiveData<List<CryptoHoldingModel>>>

    fun getCryptoIdsByName(name: String): Either<Failure, LiveData<List<CryptoModel>>>

    fun getSearchInfo(ids: String): Either<Failure, LiveData<List<CryptoInfoModel>>>

    fun insertIntoCrypto(listCryptoModel: List<CryptoModel>): Either<Failure, UseCase.None>

    fun insertIntoHoldings(listCryptoHolding: List<CryptoHoldingModel>): Either<Failure, UseCase.None>

    fun updateCryptoHoldingPrice(): Either<Failure, UseCase.None>

    fun deleteHoldingItem(portfolioId: Int, cryptoId: Int): Either<Failure, UseCase.None>

    fun deletePortfolioDataById(id: Int): Either<Failure, UseCase.None>

    class DefaultRepository @Inject constructor(
        val networkHandler: NetworkHandler,
        val coinApi: CoinApi,
        val cryptoDao: CryptoDao,
        val cryptoHoldingsDao: CryptoHoldingsDao
    ) :
        CryptoRepository {


        override fun getAllCrypto(): Either<Failure, LiveData<List<CryptoModel>>> {
            return try {
                Either.Right(cryptoDao.getAllCryptoNormal())
            } catch (e: Exception) {
                Either.Left(Failure.DataBaseError)
            }

        }

        override fun getCryptoHoldings(): Either<Failure, LiveData<List<CryptoHoldingModel>>> {
            return try {
                Either.Right(cryptoHoldingsDao.getHoldings())
            } catch (e: Exception) {
                Either.Left(Failure.DataBaseError)
            }
        }

        override fun getCryptoIdsByName(name: String): Either<Failure, LiveData<List<CryptoModel>>> {
            return if (!name.isNullOrEmpty()) {
                if (name.length == 1) Either.Right(cryptoDao.getCryptoByName(name + "%"))
                else Either.Right(cryptoDao.getCryptoByName("%" + name + "%"))
            } else {
                Either.leftDefault()
            }
        }

        override fun getSearchInfo(ids: String): Either<Failure, LiveData<List<CryptoInfoModel>>> {
            return when (networkHandler.isConnected) {
                true -> try {
                    Either.Right(MutableLiveData(coinApi.getCryptoInfoByIds(ids).execute().body()?.data?.map { it.value }
                        ?: emptyList()))
                } catch (e: Exception) {
                    Either.Left(Failure.NetworkConnection)
                }
                else -> Either.Left(Failure.NetworkConnection)
            }

        }

        override fun insertIntoCrypto(listCryptoModel: List<CryptoModel>): Either<Failure, UseCase.None> {
            return try {
                cryptoDao.insert(listCryptoModel)
                Either.rightDefault()
            } catch (e: Exception) {
                Either.Left(Failure.DataBaseError)
            }

        }

        override fun insertIntoHoldings(listCryptoHolding: List<CryptoHoldingModel>): Either<Failure, UseCase.None> {
            return try {
                cryptoHoldingsDao.insertAll(listCryptoHolding)
                Either.rightDefault()
            } catch (e: Exception) {
                Either.Left(Failure.DataBaseError)
            }
        }

        override fun updateCryptoHoldingPrice(): Either<Failure, UseCase.None> {
            if (networkHandler.isConnected == false) return Either.Left(Failure.NetworkConnection)

            val listCryptoHoldings = cryptoHoldingsDao.getHoldings1()
            val ids = listCryptoHoldings.map { it.crypto_id }.groupBy { it }.keys.toMutableList()
            if (ids.isNotEmpty()) {
                val idsString = ids.joinToString(",")
                try {
                    val quoteList =
                        coinApi.getQuoteCoins(idsString).execute().body()?.data?.flatMap { listOf(it.value) }
                    listCryptoHoldings.forEach { cryptoHolding ->
                        run {
                            val quoteInfo = quoteList?.find { it.id == cryptoHolding.crypto_id }
                            quoteInfo?.let {
                                cryptoHolding.current_price = it.quote?.USD?.price ?: 0.0
                                cryptoHolding.percent_change_24 = it.quote?.USD?.percent_change_24h ?: 0.0
                            }
                        }
                    }
                    try {
                        cryptoHoldingsDao.insertAll(listCryptoHoldings)
                    } catch (e: Exception) {
                        return Either.Left(Failure.DataBaseError)
                    }
                } catch (e: Exception) {
                    return Either.Left(Failure.NetworkConnection)
                }
            }
            return Either.Right(UseCase.None())
        }

        override fun deleteHoldingItem(portfolioId: Int, cryptoId: Int): Either<Failure, UseCase.None> {
            return try {
                cryptoHoldingsDao.deleteHoldingItem(portfolioId, cryptoId)
                Either.rightDefault()
            } catch (e: Exception) {
                Either.Left(Failure.DataBaseError)
            }
        }

        override fun deletePortfolioDataById(id: Int): Either<Failure, UseCase.None> {
            return try {
                cryptoHoldingsDao.deleteHolding(id)
                Either.rightDefault()
            } catch (e: Exception) {
                Either.Left(Failure.DataBaseError)
            }

        }

    }
}
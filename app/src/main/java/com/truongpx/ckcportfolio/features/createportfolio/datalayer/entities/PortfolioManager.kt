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

package com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities

import com.truongpx.ckcportfolio.features.basecrypto.datalayer.GlobalConstants
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.MySharedPreferences
import com.truongpx.ckcportfolio.features.basecrypto.domainlayer.usecases.DeleteHoldingItemByPortfolioIdUC

class PortfolioManager constructor(@Transient val mySharedPreferences: MySharedPreferences, @Transient val deleteHoldingItemByPortfolioIdUC: DeleteHoldingItemByPortfolioIdUC) {

    private var listPortfolio: ArrayList<PortfolioModel> = arrayListOf()

    private var currentSelectedPortfolioId: Int

    init {
        var portfolioManagerInstance = getInstanceFromPreference()
        if (portfolioManagerInstance == null || portfolioManagerInstance.listPortfolio.isEmpty()) {
            addNewPortfolio(
                PortfolioModel(
                    id = 0,
                    name = "Main Portfolio"
                )
            )
            currentSelectedPortfolioId = 0
            updatePortfolioList()
            portfolioManagerInstance = getInstanceFromPreference()
        }
        listPortfolio = portfolioManagerInstance.listPortfolio
        currentSelectedPortfolioId = portfolioManagerInstance.getCurrentSelectedPortfolioId()
    }


    private fun getInstanceFromPreference(): PortfolioManager {
        return mySharedPreferences[GlobalConstants.PORTFOLIO_LIST, PortfolioManager::class.java]
    }

    fun getPortfolioList(): List<PortfolioModel> = listPortfolio

    private fun getCurrentSelectedPortfolioId(): Int {
        return currentSelectedPortfolioId
    }

    fun getCurrentSelectedIndex(): Int {
        var portfolioPageIndex = 0
        listPortfolio.filterIndexed { index, portfolioModel ->
            if (portfolioModel.id == getCurrentSelectedPortfolioId()) portfolioPageIndex = index
            true
        }
        return portfolioPageIndex
    }

    fun getCurrentSelectedPortfolio(): PortfolioModel? =
        listPortfolio.find { it.id == getCurrentSelectedPortfolioId() }


    fun setCurrentSelectedPortfolioId(id: Int) {
        currentSelectedPortfolioId = id
        updatePortfolioList()
    }

    private fun updatePortfolioList() {
        mySharedPreferences.put(GlobalConstants.PORTFOLIO_LIST, this@PortfolioManager)
    }

    fun addNewPortfolio(portfolio: PortfolioModel) {
        listPortfolio.apply {
            add(portfolio)
            sortWith(compareBy { it?.id })
        }
        currentSelectedPortfolioId = portfolio.id
        updatePortfolioList()
    }

    private fun deletePortfolioById(id: Int?) {
        listPortfolio.filterIndexed { index, portfolioModel ->
            if (portfolioModel.id == id) currentSelectedPortfolioId = listPortfolio[index - 1].id; true
        }
        val newListPortfolio = ArrayList(listPortfolio.filter { it.id != id })
        newListPortfolio.sortWith(compareBy { it?.id })
        listPortfolio = newListPortfolio
        updatePortfolioList()
    }

    fun deleteCurrentPortfolio() {
        if (currentSelectedPortfolioId != 0) {
            val currentPortfolio = getCurrentSelectedPortfolio()
            deletePortfolioById(currentPortfolio?.id)
            currentPortfolio?.let { deleteHoldingItemByPortfolioIdUC(it.id) }
        }
    }

    fun clearCurrentPortfolio() {
        val currentPortfolio = getCurrentSelectedPortfolio()
        currentPortfolio?.let { deleteHoldingItemByPortfolioIdUC(it.id) }
    }
}
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

package com.truongpx.ckcportfolio.features.basecrypto.uilayer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.PagerItemPorfolioFragment
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioManager
import com.truongpx.ckcportfolio.features.createportfolio.datalayer.entities.PortfolioModel

class ViewPagerAdapter(fm: FragmentManager, val portfolioManager: PortfolioManager) : FragmentStatePagerAdapter(fm) {

    private var portfolioFragmentList: MutableList<PagerItemPorfolioFragment> = arrayListOf(
        PagerItemPorfolioFragment()
    )

    private var lastFragment = PagerItemPorfolioFragment().apply { setUpEmpty() }

    private var listHolding: List<CryptoHoldingModel> = emptyList()

    private var holdingDataList: MutableList<List<CryptoHoldingModel>> = arrayListOf(ArrayList())

    private var portfolioDataList: MutableList<PortfolioModel> = arrayListOf()


    override fun getItem(position: Int): Fragment =
        when {
            isLastItem(position) -> lastFragment
            else -> portfolioFragmentList[position]
        }


    override fun getItemPosition(`object`: Any): Int =
        PagerAdapter.POSITION_NONE

    fun getCurrentHoldingData(): List<CryptoHoldingModel> {
        var currentIndex = getCurrentSelectedIndex()
        return if (currentIndex < holdingDataList.size)
            holdingDataList[currentIndex]
        else emptyList()
    }

    fun getPortfolioData(position: Int) = portfolioDataList[position]

    fun getCurrentSelectedIndex() = portfolioManager.getCurrentSelectedIndex()


    fun setHoldingData(listHolding: List<CryptoHoldingModel>) {
        this.listHolding = listHolding
    }


    fun updatePortfolioDataListChanged() {
        portfolioFragmentList.clear()
        holdingDataList.clear()
        portfolioDataList.clear()
        portfolioDataList.addAll(portfolioManager.getPortfolioList())
        for (i in 0 until portfolioDataList.size) {
            val portfolioId = portfolioDataList[i].id
            val portfolioName = portfolioDataList[i].name
            val portfolio = listHolding.filter { it.portfolio_Id == portfolioId }
            holdingDataList.add(portfolio)
            portfolioFragmentList.add(PagerItemPorfolioFragment.newInstance(portfolioName, portfolio))
        }
        notifyDataSetChanged()
    }

//    override fun getPageWidth(position: Int) = 0.9f


    override fun getCount() = if (portfolioDataList.size > 0) portfolioDataList.size + 1 else 0

    fun isLastItem(position: Int) = count > 0 && position == count - 1
}
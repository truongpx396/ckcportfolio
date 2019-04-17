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

package com.truongpx.ckcportfolio.features.basecrypto.uilayer.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingInfo

class HoldingPagerItemView : RelativeLayout {

    lateinit var portfolioName: TextView

    lateinit var sumValue: TextView

    lateinit var valueChange: TextView

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    companion object {
        fun newInstance(layoutInflater: LayoutInflater, parent: ViewGroup): HoldingPagerItemView {
            return layoutInflater.inflate(R.layout.item_pager_portfolio, parent, false) as HoldingPagerItemView
        }
    }

    fun bindingData(holdingList: List<CryptoHoldingInfo>) {
        sumValue.text = holdingList.map { it.cyptoHoldingModel.holding_amount }.sum().toString()
        valueChange.text = holdingList.map { it.cryptoList[0].quote!!.USD.percent_change_24h }.average().toString()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        portfolioName = findViewById(R.id.text_portfolioName)
        sumValue = findViewById(R.id.text_sumValue)
        valueChange = findViewById(R.id.text_valueChange)
    }

}
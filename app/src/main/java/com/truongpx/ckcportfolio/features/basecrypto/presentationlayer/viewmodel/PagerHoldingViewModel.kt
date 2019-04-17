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

package com.truongpx.ckcportfolio.features.basecrypto.presentationlayer.viewmodel

import com.truongpx.ckcportfolio.core.extension.format
import com.truongpx.ckcportfolio.core.platform.BaseViewModel
import com.truongpx.ckcportfolio.features.basecrypto.datalayer.entities.CryptoHoldingModel
import javax.inject.Inject

class PagerHoldingViewModel @Inject constructor() : BaseViewModel() {

    var holding: List<CryptoHoldingModel>? = null

    var portfolioName = ""
    var sumValue = ""
    var valueChange24 = 0.0
    var valueChange24Text = ""


    fun setData(name: String, holdingList: List<CryptoHoldingModel>) {
        if (holding == null) {
            holding = holdingList
            portfolioName = name
            sumValue = formatDataHolding(holding!!.map { it.holdingValue }.sum())
            valueChange24 = holding!!.map { it.percent_change_24 }.average()
            valueChange24Text = if (valueChange24 >= 0) "+${valueChange24.format(2)}%"
            else "${valueChange24.format(2)}%"
        }
    }

    private fun formatDataHolding(value: Double): String {
        return if (value < 1.0)
            "\$${value.format(7)}"
        else "\$${value.format(2)}"
    }


}
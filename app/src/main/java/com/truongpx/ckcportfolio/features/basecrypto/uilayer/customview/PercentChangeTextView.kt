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
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import com.truongpx.ckcportfolio.R

class PercentChangeTextView : TextView {

    private var mColorPriceIncreased = getColor(R.color.colorPriceIncreased)

    private var mColorPriceDecreased = getColor(R.color.colorPriceDecreased)

//    private var mDrawablePlusIcon = resources.getDrawable(R.drawable.ic_price_up,null)

//    private var mDrawableMinusIcon = resources.getDrawable(R.drawable.ic_price_down,null)

    private var mDrawableArrowUp = resources.getDrawable(R.drawable.ic_price_up,null)

    private var mDrawableArrowDown = resources.getDrawable(R.drawable.ic_price_down,null)

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defTypeAttribute: Int) : super(
        context,
        attributeSet,
        defTypeAttribute
    )

    private fun getColor(resouceId: Int) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(resouceId, null)
    } else {
        resources.getColor(resouceId)
    }

    fun bindTo(value: Double) {
        if (value >= 0) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableArrowUp, null)
            setTextColor(mColorPriceIncreased)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableArrowDown, null)
            setTextColor(mColorPriceDecreased)
        }
    }


}
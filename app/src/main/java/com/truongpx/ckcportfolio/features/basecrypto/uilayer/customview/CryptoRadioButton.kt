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
import android.view.View
import android.widget.RadioButton
import com.truongpx.ckcportfolio.R
import com.truongpx.corelibrary.extension.setUpAnimation

class CryptoRadioButton : RadioButton {

    var stateDown: Boolean
    var prevStateChecked: Boolean
    lateinit var onItemClickListener: (View) -> Unit


    private val arrowUpDrawable = resources.getDrawable(R.drawable.ic_arrow_up, null)
    private val arrowDownDrawable = resources.getDrawable(R.drawable.ic_arrow_down, null)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        stateDown = true
        prevStateChecked = false
        setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDownDrawable, null)
        setOnClickListener {
            when (prevStateChecked) {
                true -> prevStateChecked = false
                else -> if (isChecked) switchState()
            }
            if (isChecked) onItemClickListener(it)
        }



        setUpAnimation()

    }

    fun onCheckedChangeListener(isChecked: Boolean) {
        prevStateChecked = isChecked
    }

    val IsStateDown: Boolean
        get() = stateDown

    private fun switchState() {
        stateDown = !stateDown
        if (stateDown) setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDownDrawable, null)
        else setCompoundDrawablesWithIntrinsicBounds(null, null, arrowUpDrawable, null)
    }

}
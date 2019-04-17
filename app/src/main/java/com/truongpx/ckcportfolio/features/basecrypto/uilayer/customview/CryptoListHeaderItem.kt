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
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import com.truongpx.ckcportfolio.R

class CryptoListHeaderItem : TextView {

    private var state: State =
        State.Disable_Down

    private val arrowUpDrawable = resources.getDrawable(android.R.drawable.arrow_up_float, null)
    private val arrowDownDrawable = resources.getDrawable(android.R.drawable.arrow_down_float, null)

    private val ENABLE_COLOR_CODE = android.R.color.white
    private val DISABLE_COLOR_CODE = R.color.colorWhite50p

    fun onClickListner(view: CryptoListHeaderItem) : (CryptoListHeaderItem) -> Unit = {}

    private val DISABLE_COLOR = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(
        DISABLE_COLOR_CODE,
        null
    ) else resources.getColor(DISABLE_COLOR_CODE)
    private val ENABLE_COLOR = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColor(
        ENABLE_COLOR_CODE,
        null
    ) else resources.getColor(ENABLE_COLOR_CODE)

    enum class State {
        Enable_Up, Enable_Down, Disable_Up, Disable_Down
    }

    constructor(context: Context,attributes: AttributeSet) : super(context,attributes) {
        setOnClickListener {
            when (state) {
                State.Disable_Down -> {
                    state =
                        State.Enable_Down
                    setTextLeftDrawable(arrowDownDrawable)
                    setTextColor(ENABLE_COLOR)
                }
                State.Disable_Up -> {
                    state =
                        State.Enable_Up
                    setTextLeftDrawable(arrowUpDrawable)
                    setTextColor(DISABLE_COLOR)
                }
                State.Enable_Down -> {
                    state =
                        State.Enable_Up
                    setTextLeftDrawable(arrowUpDrawable)
                    setTextColor(ENABLE_COLOR)
                }
                State.Enable_Up -> {
                    state =
                        State.Enable_Down
                    setTextLeftDrawable(arrowDownDrawable)
                    setTextColor(DISABLE_COLOR)
                }
            }

            this@CryptoListHeaderItem.onClickListner(this)
        }
    }

    val currentState : State
            get() = state

    private fun disableText(){
        when(state){
            State.Enable_Down, State.Disable_Down -> state=
                State.Disable_Down
            State.Enable_Up, State.Disable_Up -> state=
                State.Disable_Up
        }
        setTextColor(DISABLE_COLOR)
    }

    private fun setTextLeftDrawable(drawable: Drawable) {
        setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

    }

}
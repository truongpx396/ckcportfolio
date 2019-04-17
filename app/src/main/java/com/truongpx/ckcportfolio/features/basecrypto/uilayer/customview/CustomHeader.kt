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
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.appbar.AppBarLayout
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.core.extension.setUpAnimation

class CustomHeader : AppBarLayout {

    lateinit var titleView: TextView

    lateinit var closeButton: ImageButton

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    companion object {
        fun newInstance(layoutInflater: LayoutInflater, parent: ViewGroup): CustomHeader {
            return layoutInflater.inflate(R.layout.toolbar_custom, parent, false) as CustomHeader
        }
    }

    fun setHeaderTitle(name: String) {
        titleView.text = name
    }

    fun setHeaderListener(listener: (View) -> Unit) {
        titleView.setOnClickListener { listener }
    }

    fun setRightButtonListner(listener: (View) -> Unit) {
        closeButton.setOnClickListener(listener)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        titleView = findViewById(R.id.text_portfolioName)
        closeButton = findViewById(R.id.imagebutton_exit)
        closeButton.setUpAnimation()
    }

}
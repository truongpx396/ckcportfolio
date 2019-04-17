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

package com.truongpx.ckcportfolio.features.settings.uilayer.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.truongpx.ckcportfolio.R

class SettingItem : RelativeLayout {

    lateinit var titleText: TextView

    lateinit var statusText: TextView

    constructor(context: Context, attributesSets: AttributeSet) : super(context, attributesSets)

    fun setTitle(title : String){
        titleText.setText(title)
    }

    fun setStatus(status: String){
        statusText.setText(status)
    }

    fun hideTitle(){
        titleText.visibility= View.GONE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        titleText = findViewById(R.id.text_title_setting)
        statusText = findViewById(R.id.text_status_setting)
    }
}
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
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * Created by Udit on 08/05/18.
 */
class FlatRadioGroup : ConstraintHelper {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val radioViews = ArrayList<RadioButton>()
    private var skipCheckingViewsRecursively = false
    private var hasExisted = false
    private var currentSelectedViewId: Int = -1
    var onClickListener: (View) -> Unit = {}

    private val childCheckChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        Log.d("FlatRadioGroup", "$buttonView is, checked? : $isChecked")
        if (skipCheckingViewsRecursively) {
            return@OnCheckedChangeListener
        }
        if (buttonView is CryptoRadioButton) buttonView.onCheckedChangeListener(isChecked)

        if (currentSelectedViewId != -1) {
            skipCheckingViewsRecursively = true
            for (view in radioViews) {
                if (view.getId() == currentSelectedViewId) {
                    view.isChecked = false
                    break
                }
                skipCheckingViewsRecursively = false
            }
            currentSelectedViewId = buttonView.id
            buttonView.isChecked = true

        }
    }


    override fun init(attrs: AttributeSet?) {
        super.init(attrs)
        this.mUseViewMeasure = false
    }

    override fun updatePreLayout(container: ConstraintLayout) {
        if (hasExisted) return
        hasExisted = true
        for (i in 0 until this.mCount) {
            val id = this.mIds[i]
            Log.d("FlatRadioGroup", "$id")
            val view = container.getViewById(id)
            if (view != null && view is RadioButton) {
                if (view.isChecked) currentSelectedViewId = view.id
                radioViews.add(view)
                view.setOnCheckedChangeListener(childCheckChangeListener)
            }
        }

        radioViews.forEach {
            if (it is CryptoRadioButton) it.onItemClickListener = onClickListener
        }

    }

    override fun updatePostLayout(container: ConstraintLayout?) {
        val params = this.layoutParams as ConstraintLayout.LayoutParams
        params.width = 0
        params.height = 0
    }


    fun setOnItemClickListener(@Nullable onClickListener: (View) -> Unit) {
        this.onClickListener = onClickListener
    }

    fun clearSelection() {
        if (currentSelectedViewId != -1) {
            skipCheckingViewsRecursively = true
            for (view in radioViews) {
                if (view.getId() == currentSelectedViewId) {
                    view.isChecked = false
                    break
                }
            }
            skipCheckingViewsRecursively = false
            currentSelectedViewId = -1
        }
    }
}


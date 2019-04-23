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

package com.truongpx.corelibrary.utility.customview

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.AttributeSet
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.truongpx.corelibrary.R

class CenteredTitleToolbar : Toolbar {

    private var mTitleTextView: TextView? = null
    private var mScreenWidth: Int = 0
    private var mCenterTitle = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mScreenWidth = screenSize.x

        mTitleTextView = TextView(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            mTitleTextView!!.setTextAppearance(R.style.ToolbarTitleText)
        else mTitleTextView!!.setTextAppearance(context, R.style.ToolbarTitleText)

        addView(mTitleTextView)
    }

    private val screenSize: Point
        get() {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val screenSize = Point()
            display.getSize(screenSize)
            return screenSize
        }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (mCenterTitle) {
            val location = IntArray(2)
            mTitleTextView!!.getLocationOnScreen(location)
            mTitleTextView!!.translationX =
                mTitleTextView!!.translationX + (-location[0] + mScreenWidth / 2 - mTitleTextView!!.width / 2)
        }
    }

    override fun setTitle(title: CharSequence) {
        mTitleTextView!!.text = title
        requestLayout()
    }

    override fun setTitle(titleRes: Int) {
        title = context.getString(titleRes)
        requestLayout()
    }

    fun setTitleCentered(centered: Boolean) {
        mCenterTitle = centered
        requestLayout()
    }
}
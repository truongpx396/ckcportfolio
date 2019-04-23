/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truongpx.corelibrary.extension

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

private val View.FADE_OUT_ANIMATION_DURATION: Long
    get() = 150L

private val View.FADE_IN_ANIMATION_DURATION: Long
    get() = 400L

private val View.fadeOutAnimation: AlphaAnimation
    get() = AlphaAnimation(alpha, 0.15f).apply { duration = FADE_OUT_ANIMATION_DURATION }

private val View.fadeInAnimation
    get() = AlphaAnimation(alpha, 1f).apply { duration = FADE_IN_ANIMATION_DURATION }

fun View.setUpAnimation() {
    setOnTouchListener { v, event ->
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                v?.startAnimation(fadeOutAnimation)
                false
            }
            MotionEvent.ACTION_UP
                , MotionEvent.ACTION_CANCEL
            -> {
                v?.startAnimation(fadeInAnimation)
                false
            }
            else -> false
        }
    }

}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ViewGroup.bindingInflate(@LayoutRes layoutRes: Int): ViewDataBinding =
    DataBindingUtil.inflate(LayoutInflater.from(this.context), layoutRes, this, false)

fun ImageView.loadFromUrl(url: String) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)




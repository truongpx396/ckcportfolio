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

package com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.databinding.DialogCommonBinding


abstract class AbstractDialog : Dialog, View.OnClickListener {

    private var binding: DialogCommonBinding

    private var okListener: (View) -> Unit = {}

    private var cancelListener: (View) -> Unit = {}

    constructor(context: Context) : super(context) {
        window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(true)
        window?.attributes?.windowAnimations = R.style.PauseDialogAnimation

        binding = DialogCommonBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)

        binding.apply {
            tvTitle.setBackgroundResource(getHeaderColor())
            tvTitle.setCompoundDrawablesWithIntrinsicBounds(getIconResourceId(), 0, 0, 0)
            btnCancel.setOnClickListener(this@AbstractDialog)
            btnOk.setOnClickListener(this@AbstractDialog)
        }


    }

    protected open fun show(title: String = "", message: String, buttonInfo: DialogButtonInfo? = null) {
        show(title, message, null, buttonInfo)
    }

    protected open fun show(
        title: String = "",
        message: String,
        buttonCancelInfo: DialogButtonInfo? = null,
        buttonOkInfo: DialogButtonInfo?
    ) {
        binding.apply {
            tvTitle.text = title
            tvMessage.text = message
        }
        buttonCancelInfo?.apply {
            binding.btnCancel.visibility = View.VISIBLE
            if (!buttonText.isEmpty()) binding.btnCancel.text = buttonText
        }
        buttonOkInfo?.apply {
            binding.btnOk.text = buttonText
            okListener = buttonListener
        }

        var scanActivity =
            scanForActivity(
                context
            )
        if (scanActivity != null && !scanActivity.isFinishing) {

            show()
        }
    }

    companion object {
        fun scanForActivity(context: Context): Activity? {
            return when (context) {
                null -> null
                is Activity -> context
                is ContextWrapper -> scanForActivity(
                    context.baseContext
                )
                else -> null
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_cancel -> cancelListener(v)
            R.id.btn_ok -> okListener(v)
        }
        dismiss()

    }

    abstract fun getIconResourceId(): Int

    abstract fun getHeaderColor(): Int
}
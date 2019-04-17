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

import android.content.Context
import com.truongpx.ckcportfolio.R

class ConfirmationDialog(context: Context) : AbstractDialog(context) {
    override fun getIconResourceId() = R.drawable.ic_dialog_help

    override fun getHeaderColor() = R.color.confirm_header

    override fun show(
        title: String,
        message: String,
        buttonCancelInfo: DialogButtonInfo?,
        buttonOkInfo: DialogButtonInfo?
    ) {
        super.show(
            if (title.isEmpty()) context.getString(R.string.confirmation) else title,
            message,
            buttonCancelInfo,
            buttonOkInfo
        )
    }

    fun show(message: String, buttonOkInfo: DialogButtonInfo?){
        this.show("",message,
            DialogButtonInfo(),buttonOkInfo)
    }

}
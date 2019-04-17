package com.truongpx.ckcportfolio.core.platform

import com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog.DialogButtonInfo

interface IMessageHandler {
    fun showMessage(title: String, message: String, buttonOkInfo: DialogButtonInfo)

    fun showMessage(message: String)

    fun showError(title: String, message: String, buttonOkInfo: DialogButtonInfo)

    fun showError(message: String)

    fun showConfirmation(
        title: String,
        message: String,
        buttonCancelInfo: DialogButtonInfo,
        buttonOkInfo: DialogButtonInfo
    )

    fun showConfirmation(message: String, buttonOkInfo: DialogButtonInfo)

    fun hideMessage()

}
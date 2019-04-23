package com.truongpx.corelibrary.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.truongpx.corelibrary.R
import com.truongpx.corelibrary.extension.inTransaction
import com.truongpx.corelibrary.platform.dialog.ConfirmationDialog
import com.truongpx.corelibrary.platform.dialog.DialogButtonInfo
import com.truongpx.corelibrary.platform.dialog.ErrorDialog
import com.truongpx.corelibrary.platform.dialog.SuccessDialog
import io.fabric.sdk.android.Fabric


abstract class AbstractActivity : AppCompatActivity(), IMessageHandler {


    lateinit var errorDialog: ErrorDialog

    lateinit var successDialog: SuccessDialog

    lateinit var confirmationDialog: ConfirmationDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())

        errorDialog = ErrorDialog(this)
        successDialog = SuccessDialog(this)
        confirmationDialog = ConfirmationDialog(this)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else
            finish()
    }


    override fun showMessage(title: String, message: String, buttonOkInfo: DialogButtonInfo) {
    }

    override fun showMessage(message: String) {
        successDialog.show(message)
    }

    override fun showError(title: String, message: String, buttonOkInfo: DialogButtonInfo) {

    }

    override fun showError(message: String) {
        errorDialog.show(message)
    }

    override fun showConfirmation(
        title: String,
        message: String,
        buttonCancelInfo: DialogButtonInfo,
        buttonOkInfo: DialogButtonInfo
    ) {
    }

    override fun showConfirmation(message: String, buttonOkInfo: DialogButtonInfo) {
        confirmationDialog.show(message, buttonOkInfo)
    }

    override fun hideMessage() {
        errorDialog.dismiss()

        successDialog.dismiss()

        confirmationDialog.dismiss()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        view?.let { v ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
        }
    }

    fun getRootView() = findViewById<View>(getRootLayoutId())

    abstract fun getRootLayoutId(): Int


    fun addFragment(fragment: Fragment) =
        supportFragmentManager.inTransaction {
            setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            add(getRootLayoutId(), fragment, fragment.tag)
            addToBackStack(fragment.tag)
        }


}

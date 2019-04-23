package com.truongpx.corelibrary.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.truongpx.corelibrary.R
import com.truongpx.corelibrary.extension.appContext
import com.truongpx.corelibrary.extension.viewContainer
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


abstract class AbstractFragment : Fragment() {

    lateinit var messageHandler: IMessageHandler

    lateinit var mActivity: AbstractActivity


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AbstractActivity) mActivity = context
        if (context is IMessageHandler) messageHandler = context
    }


    protected fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    protected fun showActionBarProgress() = progressStatus(View.VISIBLE)

    protected fun hideActionBarProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(mActivity) { this.progress.visibility = viewStatus }

    protected fun addFragment(fragment: Fragment) =
        with(mActivity) { this.addFragment(fragment) }

    protected fun onBackButtonPress() =
        with(mActivity) { this.onBackPressed() }

    fun hideKeyboard() {
        mActivity.hideKeyboard()
    }

    protected fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    protected fun notify(message: String) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    protected fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                appContext,
                R.color.textPrimary
            )
        )
        snackBar.show()
    }
}

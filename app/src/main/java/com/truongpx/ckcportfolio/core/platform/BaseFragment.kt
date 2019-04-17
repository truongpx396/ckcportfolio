
package com.truongpx.ckcportfolio.core.platform

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.truongpx.ckcportfolio.AndroidApplication
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.core.di.ApplicationComponent
import com.truongpx.ckcportfolio.core.extension.appContext
import com.truongpx.ckcportfolio.core.extension.viewContainer
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    lateinit var messageHandler: IMessageHandler

    lateinit var mActivity: BaseActivity

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) mActivity = context
        if (context is IMessageHandler) messageHandler = context
    }



    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun showActionBarProgress() = progressStatus(View.VISIBLE)

    internal fun hideActionBarProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(mActivity) {  this.progress.visibility = viewStatus }

    internal fun addFragment(fragment: Fragment) =
        with(mActivity) {  this.addFragment(fragment) }

    internal fun onBackButtonPress() =
        with(mActivity) {  this.onBackPressed() }

    internal fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notify(message: String) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun notifyWithAction(@StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(
            ContextCompat.getColor(
                appContext,
                R.color.colorTextPrimary
            )
        )
        snackBar.show()
    }
}

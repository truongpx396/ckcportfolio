
package com.truongpx.ckcportfolio.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.crashlytics.android.Crashlytics
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.core.extension.inTransaction
import com.truongpx.ckcportfolio.databinding.ActivityLayoutBinding
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog.ConfirmationDialog
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog.DialogButtonInfo
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog.ErrorDialog
import com.truongpx.ckcportfolio.features.basecrypto.uilayer.dialog.SuccessDialog
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*



abstract class BaseActivity : AppCompatActivity(), IMessageHandler {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navigation: NavController
    lateinit var binding: ActivityLayoutBinding

    lateinit var errorDialog: ErrorDialog

    lateinit var successDialog: SuccessDialog

    lateinit var confirmationDialog: ConfirmationDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_layout)
        navigation = Navigation.findNavController(this, R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.crypto_fragment,
            R.id.news_fragment,
            R.id.settings_fragment,
            R.id.signals_fragment
        ).build()

        setSupportActionBar(binding.header.toolbar)
        setupActionBarWithNavController(navigation, appBarConfiguration)


        navigation.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = destination.label
        }


        errorDialog = ErrorDialog(this)
        successDialog = SuccessDialog(this)
        confirmationDialog = ConfirmationDialog(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigation.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
//        (supportFragmentManager.findFragmentById(
//            R.id.root_layout
//        ) as BaseFragment).onBackPressed()
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

//    private fun addFragment(savedInstanceState: Bundle?) =
//            savedInstanceState ?: supportFragmentManager.inTransaction { add(
//                    id.fragmentContainer, fragment()) }

    fun addFragment(fragment: Fragment) =
        supportFragmentManager.inTransaction {
            setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right,
                R.anim.slide_out_right
            )
            add(R.id.root_layout, fragment, fragment.tag)
            addToBackStack(fragment.tag)
        }


}

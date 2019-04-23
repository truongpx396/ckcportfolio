package com.truongpx.ckcportfolio.platform

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.truongpx.ckcportfolio.R
import com.truongpx.ckcportfolio.databinding.ActivityLayoutBinding
import com.truongpx.corelibrary.platform.AbstractActivity
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

open class BaseActivity : AbstractActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var navigation: NavController

    lateinit var binding: ActivityLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_layout)
        navigation = Navigation.findNavController(this, R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration.Builder(

        ).build()

        setSupportActionBar(binding.header.toolbar)
        setupActionBarWithNavController(navigation, appBarConfiguration)


        navigation.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = destination.label
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigation.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun getRootLayoutId() = R.id.root_layout
}
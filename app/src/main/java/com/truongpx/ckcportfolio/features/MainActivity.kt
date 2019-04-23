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

package com.truongpx.ckcportfolio.features

import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.truongpx.ckcportfolio.R
import com.truongpx.corelibrary.platform.AbstractActivity
import com.truongpx.ckcportfolio.features.searchcrypto.uilayer.SearchCryptoFragment
import com.truongpx.ckcportfolio.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_layout.*

class MainActivity : BaseActivity() {

    private var CURRENT_SELECTED_BOTTOM_MENU = R.id.bottom_menu_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        NavigationUI.setupWithNavController(bottom_navigation, navigation)

        binding.addCryptoButton.setOnClickListener {
            addFragment(SearchCryptoFragment())
        }


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            if (it.itemId != CURRENT_SELECTED_BOTTOM_MENU) {
                CURRENT_SELECTED_BOTTOM_MENU = it.itemId
                when (it.itemId) {
                    R.id.bottom_menu_home -> {
                        navigation.navigate(R.id.action_to_crypto_fragment)
                        true
                    }
                    R.id.bottom_menu_signals -> {
                        navigation.navigate(R.id.action_to_signals_fragment)
                        true
                    }


                    R.id.bottom_menu_explore -> {
                        navigation.navigate(R.id.action_to_news_fragment)
                        true
                    }
                    R.id.bottom_menu_settings -> {
                        navigation.navigate(R.id.action_to_settings_fragment)
                        true
                    }
                    else -> true
                }
            }
            true
        }
    }


    fun showMsg(msg: String) = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()


}
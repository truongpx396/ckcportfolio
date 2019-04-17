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
package com.truongpx.ckcportfolio.core.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.view.View
import androidx.annotation.StringRes
import com.truongpx.ckcportfolio.core.platform.BaseActivity
import com.truongpx.ckcportfolio.core.platform.BaseFragment
import kotlinx.android.synthetic.main.activity_layout.*

//import kotlinx.android.synthetic.main.activity_layout.fragmentContainer

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseFragment.close() = fragmentManager?.popBackStack()


//val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).root_layout

//val BaseFragment.navController: NavController get() = (activity as BaseActivity).navigation

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun BaseFragment.getString(@StringRes id: Int): String = appContext.getString(id)
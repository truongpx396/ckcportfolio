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

package com.truongpx.ckcportfolio.features.settings.uilayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.truongpx.ckcportfolio.R
import com.truongpx.corelibrary.extension.setUpAnimation
import com.truongpx.corelibrary.platform.AbstractFragment
import com.truongpx.ckcportfolio.databinding.FragmentSettingsBinding
import com.truongpx.ckcportfolio.features.settings.uilayer.customview.SettingItem
import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod


class SettingsFragment : AbstractFragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        (binding.settingPasswordLock as SettingItem).apply {
            setUpAnimation()
            setTitle(getString(R.string.setting_title_passwordlock))
            setStatus(getString(R.string.setting_disable))
        }
        (binding.settingNotifications as SettingItem).apply {
            setUpAnimation()
            setTitle(getString(R.string.setting_title_signal))
            setStatus(getString(R.string.setting_enable))
        }
        (binding.settingLanguage as SettingItem).apply {
            setUpAnimation()
            setTitle(getString(R.string.setting_title_language))
            setStatus(getString(R.string.setting_english))
        }
        (binding.settingDefaultCurrency as SettingItem).apply {
            setUpAnimation()
            setTitle(getString(R.string.setting_title_currency))
            setStatus(getString(R.string.setting_usd))
        }
        (binding.settingRating as SettingItem).apply {
            setUpAnimation()
            hideTitle()
            setStatus(getString(R.string.setting_review))
        }
        (binding.settingShare as SettingItem).apply {
            setUpAnimation()
            hideTitle()
            setStatus(getString(R.string.setting_share))
        }
        (binding.settingFeedback as SettingItem).apply {
            setUpAnimation()
            hideTitle()
            setStatus(getString(R.string.setting_feedback))
        }
        (binding.settingTerm as SettingItem).apply {
            setUpAnimation()
            hideTitle()
            setStatus(getString(R.string.setting_terms))
            statusText.movementMethod = LinkMovementMethod.getInstance()
            setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(getString(R.string.privacy_policy))
                startActivity(browserIntent)
            }
        }

        return binding.root
    }
}
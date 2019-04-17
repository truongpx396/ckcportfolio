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

package com.truongpx.ckcportfolio.features.basecrypto.datalayer

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MySharedPreferences @Inject constructor(val context: Context, val gson: Gson) {
    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    operator fun <T> get(key: String, anonymousClass: Class<T>): T {
        return when (anonymousClass) {
            String::class.java -> mSharedPreferences.getString(key, "") as T
            Boolean::class.java -> java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(key, false)) as T
            Float::class.java -> java.lang.Float.valueOf(mSharedPreferences.getFloat(key, 0f)) as T
            Int::class.java -> Integer.valueOf(mSharedPreferences.getInt(key, 0)) as T
            Long::class.java -> java.lang.Long.valueOf(mSharedPreferences.getLong(key, 0)) as T
            else -> gson.fromJson(mSharedPreferences.getString(key, ""), anonymousClass) as T
        }
    }

    operator fun <T> get(key: String, anonymousClass: Class<T>, default: T): T {
        return when (anonymousClass) {
            String::class.java -> mSharedPreferences.getString(key, default as String) as T
            Boolean::class.java -> java.lang.Boolean.valueOf(
                mSharedPreferences.getBoolean(
                    key,
                    default as Boolean
                )
            ) as T
            Float::class.java -> java.lang.Float.valueOf(mSharedPreferences.getFloat(key, default as Float)) as T
            Int::class.java -> Integer.valueOf(mSharedPreferences.getInt(key, default as Int)) as T
            Long::class.java -> java.lang.Long.valueOf(mSharedPreferences.getLong(key, default as Long)) as T
            else -> gson.fromJson(mSharedPreferences.getString(key, ""), anonymousClass) as T
                ?: default
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, gson.toJson(data))
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }

    companion object {

        private const val PREFS_NAME = "share_prefs"

        //helper Function


    }

}

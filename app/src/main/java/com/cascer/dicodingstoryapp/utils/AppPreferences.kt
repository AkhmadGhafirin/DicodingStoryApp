package com.cascer.dicodingstoryapp.utils

import android.content.SharedPreferences

class AppPreferences(sharedPreferences: SharedPreferences) {
    private var pref: SharedPreferences = sharedPreferences
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_NAME = "KEY_NAME"
    }

    var token: String
        get() {
            return pref.getString(KEY_TOKEN, "").orEmpty()
        }
        set(value) {
            editor.putString(KEY_TOKEN, value)
            editor.apply()
        }

    var name: String
        get() {
            return pref.getString(KEY_NAME, "").orEmpty()
        }
        set(value) {
            editor.putString(KEY_NAME, value)
            editor.apply()
        }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}
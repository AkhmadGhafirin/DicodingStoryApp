package com.cascer.dicodingstoryapp.utils

import android.content.SharedPreferences

class AppPreferences(sharedPreferences: SharedPreferences) {
    private var pref: SharedPreferences = sharedPreferences
    private var editor: SharedPreferences.Editor = pref.edit()

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
    }

    var token: String
        get() {
            return pref.getString(KEY_TOKEN, "").orEmpty()
        }
        set(value) {
            editor.putString(KEY_TOKEN, value)
            editor.apply()
        }

    fun logout() {
        editor.clear()
        editor.apply()
    }
}
package com.example.retofoh.util

import android.content.Context
import android.content.SharedPreferences
import com.example.retofoh.util.constants.PREFERENCE_NAME

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getData(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}
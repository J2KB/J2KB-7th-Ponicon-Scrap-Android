package com.scrap.scrap

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getId(key: String, defValue: Long): Long {
        return prefs.getLong(key, defValue)
    }

    fun getString(key: String, defValue: String?): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setId(key: String, id: Long) {
        prefs.edit().putLong(key, id).apply()
    }

    fun setString(key: String, str: String?) {
        prefs.edit().putString(key, str).apply()
    }
}

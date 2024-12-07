package com.dicoding.submission.storyapp.data.pref

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper{

    private const val PREF_NAME = "app_preferences"
    private const val KEY_TOKEN = "token"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Menyimpan token dan status login
    fun saveLoginSession(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_TOKEN, token)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

//     Menghapus sesi login
    fun clearLoginSession(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(KEY_TOKEN)
        editor.putBoolean(KEY_IS_LOGGED_IN, false)
        editor.apply()
    }

    // Mengecek status login
    fun isLoggedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Mendapatkan token
    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_TOKEN, null)
    }
}
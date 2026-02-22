package com.example.rincon_verde2.data.local

import android.content.Context

class TokenManager(private val context: Context) {
    private val prefsName = "auth_prefs"
    private val keyToken = "auth_token"

    private val prefs by lazy { context.getSharedPreferences(prefsName, Context.MODE_PRIVATE) }

    fun saveToken(token: String) {
        prefs.edit().putString(keyToken, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(keyToken, null)
    }

    fun clearToken() {
        prefs.edit().remove(keyToken).apply()
    }
}

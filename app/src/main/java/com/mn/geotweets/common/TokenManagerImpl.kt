package com.mn.geotweets.common

import android.content.Context
import com.mn.domain.common.TokenManager
import javax.inject.Inject

class TokenManagerImpl @Inject constructor(private val context: Context) : TokenManager {
    private val prefs by lazy { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }

    override fun putToken(token: String) {
        prefs.edit().putString(TOKEN, token).apply()
    }

    override fun getToken(): String {
        return prefs.getString(TOKEN, "") ?: ""
    }

    override fun putSecret(secret: String) {
        prefs.edit().putString(SECRET, secret).apply()
    }

    override fun getSecret(): String {
        return prefs.getString(SECRET, "") ?: ""
    }

    companion object {
        private const val PREFS = "geotweets"
        private const val TOKEN = "key-token"
        private const val SECRET = "key-secret"
    }
}
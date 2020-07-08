package com.mn.data.auth

import com.mn.data.common.Mapper
import com.mn.domain.usecase.auth.AuthToken

class AuthTokenMapper : Mapper<String, AuthToken> {
    override fun invoke(t: String): AuthToken {
        val params = t.split("&").associate {
            val (key, value) = it.split("=")
            key to value
        }

        val oauthToken = params[OAUTH_TOKEN] ?: ""
        val oauthTokenSecret = params[OAUTH_TOKEN_SECRET] ?: ""
        val oauthCallbackConfirmed = params[OAUTH_CALLBACK_CONFIRMED] ?: ""

        return AuthToken(oauthToken, oauthTokenSecret, oauthCallbackConfirmed.toBoolean())
    }

    companion object {
        private const val OAUTH_TOKEN = "oauth_token"
        private const val OAUTH_TOKEN_SECRET = "oauth_token_secret"
        private const val OAUTH_CALLBACK_CONFIRMED = "oauth_callback_confirmed"
    }
}
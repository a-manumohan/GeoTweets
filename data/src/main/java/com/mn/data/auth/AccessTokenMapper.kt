package com.mn.data.auth

import com.mn.data.common.Mapper
import com.mn.domain.usecase.auth.AccessToken

class AccessTokenMapper : Mapper<String, AccessToken> {
    override fun invoke(t: String): AccessToken {
        val params = t.split("&").associate {
            val (key, value) = it.split("=")
            key to value
        }
        return AccessToken(
            params[TOKEN] ?: "",
            params[SECRET] ?: "",
            params[USER_ID] ?: "",
            params[SCREEN_NAME] ?: ""
        )
    }

    companion object {
        private const val TOKEN = "oauth_token"
        private const val SECRET = "oauth_token_secret"
        private const val USER_ID = "user_id"
        private const val SCREEN_NAME = "screen_name"
    }
}
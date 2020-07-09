package com.mn.domain.common

interface TokenManager {
    fun putToken(token: String)
    fun getToken(): String

    fun putSecret(secret: String)
    fun getSecret(): String
}
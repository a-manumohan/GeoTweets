package com.mn.domain.usecase.auth

data class AuthToken(
    val oathToken: String,
    val oathTokenSecret: String,
    val oathCallBackConfirmed: Boolean
)
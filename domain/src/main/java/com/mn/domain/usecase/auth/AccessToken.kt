package com.mn.domain.usecase.auth

data class AccessToken(
    val oauthToken: String,
    val oauthTokenSecret: String,
    val userId: String,
    val screenName: String
)
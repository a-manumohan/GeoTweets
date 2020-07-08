package com.mn.data.auth

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.auth.AuthToken
import retrofit2.http.POST

interface AuthApi {
    @POST("oauth/request_token")
    fun requestAuthToken(): Either<Failure, AuthToken>
}
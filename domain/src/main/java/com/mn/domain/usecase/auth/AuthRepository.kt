package com.mn.domain.usecase.auth

import com.mn.domain.common.Either
import com.mn.domain.common.Failure

interface AuthRepository {
    suspend fun getAuthToken(callbackUrl: String): Either<Failure, AuthToken>
    suspend fun getAccessToken(token: String, verifier: String): Either<Failure, AccessToken>
}
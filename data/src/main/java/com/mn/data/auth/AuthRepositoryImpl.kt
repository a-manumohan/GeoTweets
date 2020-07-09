package com.mn.data.auth

import com.mn.data.common.request
import com.mn.data.common.withNetwork
import com.mn.domain.NetworkHandler
import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.auth.AccessToken
import com.mn.domain.usecase.auth.AuthRepository
import com.mn.domain.usecase.auth.AuthToken
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val networkHandler: NetworkHandler
) :
    AuthRepository {
    private val authTokenMapper by lazy { AuthTokenMapper() }
    private val accessTokenMapper by lazy { AccessTokenMapper() }

    override suspend fun getAuthToken(callbackUrl: String): Either<Failure, AuthToken> {
        return networkHandler.withNetwork {
            request(authApi.requestAuthToken(callbackUrl)) {
                authTokenMapper(it)
            }
        }
    }

    override suspend fun getAccessToken(
        token: String,
        verifier: String
    ): Either<Failure, AccessToken> {
        return networkHandler.withNetwork {
            request(authApi.requestAccessToken(token, verifier)) {
                accessTokenMapper(it)
            }
        }
    }
}
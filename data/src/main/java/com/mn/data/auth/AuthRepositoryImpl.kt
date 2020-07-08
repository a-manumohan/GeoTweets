package com.mn.data.auth

import com.mn.data.common.withNetwork
import com.mn.domain.NetworkHandler
import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.auth.AuthRepository
import com.mn.domain.usecase.auth.AuthToken
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val networkHandler: NetworkHandler
) :
    AuthRepository {
    override suspend fun getAuthToken(): Either<Failure, AuthToken> {
        return networkHandler.withNetwork {
            authApi.requestAuthToken()
        }
    }
}
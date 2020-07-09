package com.mn.domain.usecase.auth

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.UseCase
import javax.inject.Inject

class GetAuthToken @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<AuthToken, String>() {

    override suspend fun run(params: String): Either<Failure, AuthToken> {
        return authRepository.getAuthToken(params)
    }
}
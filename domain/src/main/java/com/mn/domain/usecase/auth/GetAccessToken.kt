package com.mn.domain.usecase.auth

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.UseCase
import javax.inject.Inject

class GetAccessToken @Inject constructor(private val authRepository: AuthRepository) :
    UseCase<AccessToken, GetAccessToken.Params>() {
    override suspend fun run(params: Params): Either<Failure, AccessToken> {
        return authRepository.getAccessToken(params.token, params.verifier)
    }

    data class Params(
        val token: String,
        val verifier: String
    )
}
package com.mn.domain.usecase.auth

import com.mn.domain.common.Either
import com.mn.domain.common.Failure

interface AuthRepository {
    suspend fun getAuthToken(): Either<Failure, AuthToken>
}
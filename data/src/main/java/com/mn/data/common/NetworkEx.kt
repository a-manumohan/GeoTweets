package com.mn.data.common

import com.mn.domain.NetworkHandler
import com.mn.domain.common.Either
import com.mn.domain.common.Failure


internal suspend fun <T> NetworkHandler.withNetwork(f: suspend () -> Either<Failure, T>): Either<Failure, T> {
    return if (isConnected) {
        f()
    } else {
        Either.Left(Failure.NetworkError)
    }
}

internal fun <T> NetworkHandler.withNetworkSync(f: () -> Either<Failure, T>): Either<Failure, T> {
    return if (isConnected) {
        f()
    } else {
        Either.Left(Failure.NetworkError)
    }
}
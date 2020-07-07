package com.mn.data.common

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import getServerError
import retrofit2.Call
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
    return try {
        val response = call.execute()
        when (response.isSuccessful) {
            true -> Either.Right(transform((response.body() ?: default)))
            false -> Either.Left(response.code().getServerError())
        }
    } catch (ce: ConnectException) {
        Either.Left(Failure.NetworkError)
    } catch (se: SocketException) {
        Either.Left(Failure.NetworkError)
    } catch (uhe: UnknownHostException) {
        Either.Left(Failure.NetworkError)
    } catch (ste: SocketTimeoutException) {
        Either.Left(Failure.NetworkError)
    }
}

internal fun <T, R> request(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
    return try {
        val response = call.execute()
        when (response.isSuccessful) {
            true -> safeTransform(response, transform)
            false -> Either.Left(response.code().getServerError())
        }
    } catch (ce: ConnectException) {
        Either.Left(Failure.NetworkError)
    } catch (se: SocketException) {
        Either.Left(Failure.NetworkError)
    } catch (uhe: UnknownHostException) {
        Either.Left(Failure.NetworkError)
    } catch (ste: SocketTimeoutException) {
        Either.Left(Failure.NetworkError)
    }
}

private fun <R, T> safeTransform(
    response: Response<T>,
    transform: (T) -> R
): Either<Failure, R> {
    return response.body()?.let {
        Either.Right(transform((it)))
    } ?: Either.Left(Failure.UnknownError)
}
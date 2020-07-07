package com.mn.domain.common

sealed class Failure {

    object NetworkError : Failure()

    object UnknownError : Failure()

    data class GenericException(val messages: List<String>) : Failure()

    sealed class ServerError : Failure() {
        object BadRequest : ServerError()

        object Unauthorized : ServerError()

        object NotFound : ServerError()

        object InternalServerError : ServerError()
    }
}
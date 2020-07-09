package com.mn.geotweets.feature.auth

interface AuthResult {
    sealed class State
    sealed class Event {
        object GoToMap : Event()
    }

    sealed class Error {
        object NetworkError : Error()
        object ServerError : Error()
        object UnknownError : Error()
        object Unauthorized : Error()
        data class GenericError(val message: String) : Error()
    }
}
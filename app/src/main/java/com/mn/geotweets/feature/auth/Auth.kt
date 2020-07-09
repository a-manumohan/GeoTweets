package com.mn.geotweets.feature.auth

interface Auth {
    sealed class State

    sealed class Event {
        data class EnableAuthButton(val enable: Boolean) : Event()
        data class Authorize(val url: String) : Event()
    }

    sealed class Error {
        object NetworkError : Error()
        object ServerError : Error()
        object UnknownError : Error()
        object Unauthorized : Error()
        data class GenericError(val message: String) : Error()
    }
}
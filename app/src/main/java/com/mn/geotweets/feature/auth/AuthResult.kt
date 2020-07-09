package com.mn.geotweets.feature.auth

interface AuthResult {
    sealed class State
    sealed class Event
    sealed class Error
}
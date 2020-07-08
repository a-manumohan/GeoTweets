package com.mn.geotweets.feature.auth

interface Auth {
    sealed class State
    sealed class Event
    sealed class Error
}
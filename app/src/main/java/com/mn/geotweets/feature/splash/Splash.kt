package com.mn.geotweets.feature.splash

interface Splash {
    sealed class State
    sealed class Event {
        object GoToMap : Event()
        object GoToAuthorization : Event()
    }

    sealed class Error
}
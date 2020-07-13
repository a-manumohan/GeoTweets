package com.mn.geotweets.feature.details

interface TweetDetails {
    sealed class State {
        data class Details(val uiTweetDetails: UiTweetDetails) : State()
    }

    sealed class Event {
        data class PlayVideo(val url: String) : Event()
    }

    sealed class Error {
        object NetworkError : Error()
        object ServerError : Error()
        object UnknownError : Error()
        object Unauthorized : Error()
        data class GenericError(val message: String) : Error()
    }
}
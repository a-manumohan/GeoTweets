package com.mn.geotweets.feature.tweets

interface TweetMap {
    sealed class State {
        data class Tweets(val uiTweets: List<UiTweet>) : State()
    }

    sealed class Event {
        data class GoToDetails(val tweetId: String) : Event()
    }

    sealed class Error {
        object NetworkError : Error()
        object ServerError : Error()
        object UnknownError : Error()
        object Unauthorized : Error()
        data class GenericError(val message: String) : Error()
    }
}
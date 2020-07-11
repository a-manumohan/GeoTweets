package com.mn.geotweets.feature.map

interface TweetMap {
    sealed class State {
        data class Tweets(val uiTweets: List<UiTweet>) : State()
    }

    sealed class Event
    sealed class Error
}
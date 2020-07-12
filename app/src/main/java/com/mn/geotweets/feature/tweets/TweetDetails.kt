package com.mn.geotweets.feature.tweets

interface TweetDetails {
    sealed class State
    sealed class Event
    sealed class Error
}
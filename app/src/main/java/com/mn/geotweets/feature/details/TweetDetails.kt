package com.mn.geotweets.feature.details

interface TweetDetails {
    sealed class State
    sealed class Event
    sealed class Error
}
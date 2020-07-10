package com.mn.geotweets.feature.map

interface TweetMap {
    sealed class State
    sealed class Event
    sealed class Error
}
package com.mn.geotweets.feature.tweets

data class UiTweet(
    val id: String,
    val username: String,
    val text: String,
    val lat: Double,
    val lng: Double
)
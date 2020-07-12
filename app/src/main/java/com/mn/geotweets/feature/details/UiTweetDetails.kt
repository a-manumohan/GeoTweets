package com.mn.geotweets.feature.details

data class UiTweetDetails(
    val text: String,
    val imageUrl: String?,
    val userName: String,
    val date: String,
    val showPlayButton: Boolean
)
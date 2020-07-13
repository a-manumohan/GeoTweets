package com.mn.geotweets.feature.details

data class UiTweetDetails(
    val text: String,
    val imageUrl: String?,
    val name: String,
    val userName: String,
    val userImage: String?,
    val date: String,
    val showPlayButton: Boolean,
    val isReTweeted: Boolean
)
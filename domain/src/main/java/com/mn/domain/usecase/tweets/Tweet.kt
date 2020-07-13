package com.mn.domain.usecase.tweets

data class Tweet(
    val id: String,
    val user: User,
    val text: String,
    val favorited: Boolean,
    val retweeted: Boolean,
    val media: List<Media>,
    val place: Place?,
    val createdAt: String
) {
    data class User(
        val id: String,
        val screenName: String,
        val name: String,
        val pic: String?
    )

    data class Media(
        val id: String,
        val url: String,
        val type: Type
    ) {
        enum class Type {
            PHOTO,
            VIDEO
        }
    }

    data class Place(
        val lat: Double,
        val lng: Double
    )
}
package com.mn.data.tweets

import com.mn.data.common.Mapper
import com.mn.domain.usecase.tweets.Tweet

class TweetMapper : Mapper<TweetResponse, Tweet> {
    override fun invoke(t: TweetResponse): Tweet {
        return getTweet(t)
    }

    private fun getTweet(response: TweetResponse): Tweet {
        return with(response) {
            val media = getMedia(entities) + getVideoMedia(extendedEntities)
            Tweet(
                id_str,
                getUser(user),
                text,
                favorited,
                retweeted,
                media,
                getPlace(place),
                createdAt
            )
        }
    }

    private fun getPlace(place: Place?): Tweet.Place? {
        return place?.let {
            val coords = it.boundingBox.coordinates[0][0]
            Tweet.Place(
                coords[1],
                coords[0]
            )
        }
    }

    private fun getMedia(entities: Entities): List<Tweet.Media> {
        return entities.media?.map {
            Tweet.Media(
                it.id,
                it.url,
                getType(it.type)
            )
        } ?: listOf()
    }

    private fun getVideoMedia(extendedEntities: ExtendedEntities?): List<Tweet.Media> {
        return extendedEntities?.let {
            it.media?.map { media ->
                Tweet.Media(
                    media.id,
                    media.videoInfo?.variants?.get(0)?.url ?: "",
                    getType(media.type)
                )
            }
        } ?: listOf()
    }

    private fun getType(type: String): Tweet.Media.Type {
        return when (type.toLowerCase()) {
            PHOTO -> Tweet.Media.Type.PHOTO
            else -> Tweet.Media.Type.VIDEO
        }
    }

    private fun getUser(user: User): Tweet.User {
        return Tweet.User(
            user.id_str,
            user.screenName,
            user.name,
            user.profile_image_url_https
        )
    }

    companion object {
        private const val PHOTO = "photo"
        private const val VIDEO = "video"
    }
}
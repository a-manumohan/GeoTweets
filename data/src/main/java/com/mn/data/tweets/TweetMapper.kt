package com.mn.data.tweets

import com.mn.data.common.Mapper
import com.mn.domain.usecase.tweets.Tweet

class TweetMapper : Mapper<TweetResponse, Tweet> {
    override fun invoke(t: TweetResponse): Tweet {
        return getTweet(t)
    }

    private fun getTweet(response: TweetResponse): Tweet {
        return with(response) {
            Tweet(
                id_str,
                getUser(user),
                text,
                favorited,
                retweeted,
                getMedia(entities),
                getPlace(place)
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
                it.type
            )
        } ?: listOf()
    }

    private fun getUser(user: User): Tweet.User {
        return Tweet.User(
            user.id_str,
            user.screenName,
            user.name,
            user.profile_image_url_https
        )
    }
}
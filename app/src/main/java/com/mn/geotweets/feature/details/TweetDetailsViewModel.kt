package com.mn.geotweets.feature.details

import com.mn.domain.common.Failure
import com.mn.domain.usecase.tweets.GetTweet
import com.mn.domain.usecase.tweets.Tweet
import com.mn.geotweets.feature.base.BaseViewModel
import com.mn.geotweets.feature.details.TweetDetails
import javax.inject.Inject

class TweetDetailsViewModel @Inject constructor(private val getTweet: GetTweet) :
    BaseViewModel<TweetDetails.State, TweetDetails.Event, TweetDetails.Error>() {

    private var tweet: Tweet? = null

    fun playVideo() {
        tweet?.let { tweet ->
            val url = tweet.media.find { it.type == Tweet.Media.Type.VIDEO }?.url
            url?.let {
                event(TweetDetails.Event.PlayVideo(it))
            }
        }
    }

    fun fetchTweetDetails(tweetId: String) {
        getTweet(tweetId) {
            it.either(::handleFailure, ::handleTweetSuccess)
        }
    }

    private fun handleTweetSuccess(tweet: Tweet) {
        this.tweet = tweet

        val imageUrl = tweet.media.find { it.type == Tweet.Media.Type.PHOTO }?.url
        val showPlayButton = tweet.media.any { it.type == Tweet.Media.Type.VIDEO }
        UiTweetDetails(
            tweet.text,
            imageUrl,
            tweet.user.screenName,
            tweet.createdAt,
            showPlayButton
        ).let {
            state(TweetDetails.State.Details(it))
        }
    }

    private fun handleFailure(failure: Failure) {
        val err = when (failure) {
            Failure.NetworkError -> TweetDetails.Error.NetworkError
            Failure.UnknownError -> TweetDetails.Error.UnknownError
            is Failure.GenericException -> TweetDetails.Error.GenericError(
                failure.messages.joinToString(
                    "\n"
                )
            )
            Failure.ServerError.Unauthorized -> TweetDetails.Error.Unauthorized
            else -> TweetDetails.Error.ServerError
        }

        error(err)
    }
}
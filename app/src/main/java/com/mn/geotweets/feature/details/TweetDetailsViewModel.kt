package com.mn.geotweets.feature.details

import com.mn.domain.common.Failure
import com.mn.domain.usecase.tweets.GetTweet
import com.mn.domain.usecase.tweets.ReTweet
import com.mn.domain.usecase.tweets.Tweet
import com.mn.domain.usecase.tweets.UnReTweet
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class TweetDetailsViewModel @Inject constructor(
    private val getTweet: GetTweet,
    private val reTweet: ReTweet,
    private val unReTweet: UnReTweet
) :
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

    fun retweet() {
        tweet?.let {
            if (it.retweeted.not()) {
                reTweet(it.id) {
                    it.either(::handleFailure) { updateTweet(true) }
                }
            } else {
                unReTweet(it.id) {
                    it.either(::handleFailure) { updateTweet(false) }
                }
            }
        }
    }

    private fun updateTweet(retweeted: Boolean) {
        tweet = tweet?.copy(retweeted = retweeted)
        tweet?.let {
            val uiTweetDetails = getUiTweetDetails(it)
            state(TweetDetails.State.Details(uiTweetDetails))
        }
    }

    private fun handleTweetSuccess(tweet: Tweet) {
        this.tweet = tweet
        val uiTweetDetails = getUiTweetDetails(tweet)
        state(TweetDetails.State.Details(uiTweetDetails))
    }

    private fun getUiTweetDetails(tweet: Tweet): UiTweetDetails {
        val imageUrl = tweet.media.find { it.type == Tweet.Media.Type.PHOTO }?.url
        val showPlayButton = tweet.media.any { it.type == Tweet.Media.Type.VIDEO }
        return UiTweetDetails(
            tweet.text,
            imageUrl,
            tweet.user.name,
            tweet.user.screenName,
            tweet.user.pic,
            tweet.createdAt,
            showPlayButton,
            tweet.retweeted
        )
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
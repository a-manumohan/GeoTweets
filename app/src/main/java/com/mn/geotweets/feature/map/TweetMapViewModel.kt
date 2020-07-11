package com.mn.geotweets.feature.map

import com.mn.domain.common.Failure
import com.mn.domain.usecase.tweets.GetTweets
import com.mn.domain.usecase.tweets.Tweet
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class TweetMapViewModel @Inject constructor(private val getTweets: GetTweets) :
    BaseViewModel<TweetMap.State, TweetMap.Event, TweetMap.Error>() {
    init {
        fetchTweets()
    }

    private fun fetchTweets() {
        getTweets(TWEET_COUNT) {
            it.either(::handleFailure, ::handleTweetsSuccess)
        }
    }

    private fun handleTweetsSuccess(tweets: List<Tweet>) {
        tweets
            .filter { it.place != null }.map {
                UiTweet(
                    it.user.screenName,
                    it.text,
                    it.place?.lat ?: 0.0,
                    it.place?.lng ?: 0.0
                )
            }.let {
                state(TweetMap.State.Tweets(it))
            }
    }

    private fun handleFailure(failure: Failure) {
        val err = when (failure) {
            Failure.NetworkError -> TweetMap.Error.NetworkError
            Failure.UnknownError -> TweetMap.Error.UnknownError
            is Failure.GenericException -> TweetMap.Error.GenericError(
                failure.messages.joinToString(
                    "\n"
                )
            )
            Failure.ServerError.Unauthorized -> TweetMap.Error.Unauthorized
            else -> TweetMap.Error.ServerError
        }

        error(err)
    }

    companion object {
        private const val TWEET_COUNT = 100
    }
}
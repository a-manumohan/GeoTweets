package com.mn.geotweets.feature.tweets

import android.location.Location
import com.mn.domain.common.Failure
import com.mn.domain.usecase.tweets.GetTweets
import com.mn.domain.usecase.tweets.Tweet
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class TweetMapViewModel @Inject constructor(private val getTweets: GetTweets) :
    BaseViewModel<TweetMap.State, TweetMap.Event, TweetMap.Error>() {
    private var location: Location? = null


    private fun fetchTweets() {
        getTweets(TWEET_COUNT) {
            it.either(::handleFailure, ::handleTweetsSuccess)
        }
    }

    private fun handleTweetsSuccess(tweets: List<Tweet>) {
        tweets
            .filter { it.place != null }
            .filter {
                isCloseBy(it.place)
            }.map {
                UiTweet(
                    it.id,
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

    private fun isCloseBy(place: Tweet.Place?): Boolean {
        return place?.let {
            val loc = Location("geo")
            loc.latitude = it.lat
            loc.longitude = it.lng
            val distance = this.location?.distanceTo(loc) ?: 0f
            distance <= DISTANCE
        } ?: false
    }

    fun locationReceived(location: Location) {
        this.location = location
        fetchTweets()
    }

    fun onMarkerClicked(id: String) {
        event(TweetMap.Event.GoToDetails(id))
    }

    fun locationFailed() {

    }

    companion object {
        private const val TWEET_COUNT = 100
        private const val DISTANCE = 5000f
    }
}
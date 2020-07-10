package com.mn.geotweets.feature.map

import com.mn.domain.usecase.tweets.GetTweets
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class TweetMapViewModel @Inject constructor(private val getTweets: GetTweets) :
    BaseViewModel<TweetMap.State, TweetMap.Event, TweetMap.Error>()
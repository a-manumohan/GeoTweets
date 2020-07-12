package com.mn.geotweets.feature.details

import com.mn.geotweets.feature.base.BaseViewModel
import com.mn.geotweets.feature.details.TweetDetails
import javax.inject.Inject

class TweetDetailsViewModel @Inject constructor() :
    BaseViewModel<TweetDetails.State, TweetDetails.Event, TweetDetails.Error>() {
}
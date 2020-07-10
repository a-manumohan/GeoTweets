package com.mn.data.tweets

import com.mn.data.common.Mapper
import com.mn.domain.usecase.tweets.Tweet

class TweetsMapper : Mapper<TweetResponse, List<Tweet>> {
    override fun invoke(t: TweetResponse): List<Tweet> {
        TODO("Not yet implemented")
    }
}
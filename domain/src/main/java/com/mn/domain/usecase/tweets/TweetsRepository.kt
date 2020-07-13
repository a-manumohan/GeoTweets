package com.mn.domain.usecase.tweets

import com.mn.domain.common.Either
import com.mn.domain.common.Failure

interface TweetsRepository {
    suspend fun getTweets(count: Int): Either<Failure, List<Tweet>>

    suspend fun getTweet(id: String): Either<Failure, Tweet>

    suspend fun retweet(id: String): Either<Failure, Unit>

    suspend fun unReTweet(id: String): Either<Failure, Unit>
}
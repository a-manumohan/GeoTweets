package com.mn.domain.usecase.tweets

import com.mn.domain.common.Either
import com.mn.domain.common.Failure

interface TweetsRepository {
    suspend fun getTweets(): Either<Failure, List<Tweet>>
}
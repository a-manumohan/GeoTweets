package com.mn.domain.usecase.tweets

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.UseCase
import javax.inject.Inject

class GetTweets @Inject constructor(private val tweetsRepository: TweetsRepository) :
    UseCase<List<Tweet>, Int>() {
    override suspend fun run(params: Int): Either<Failure, List<Tweet>> {
        return tweetsRepository.getTweets(params)
    }
}
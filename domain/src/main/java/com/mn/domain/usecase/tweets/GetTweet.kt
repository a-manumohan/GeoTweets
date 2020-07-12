package com.mn.domain.usecase.tweets

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.UseCase
import javax.inject.Inject

class GetTweet @Inject constructor(private val tweetsRepository: TweetsRepository) :
    UseCase<Tweet, String>() {
    override suspend fun run(params: String): Either<Failure, Tweet> {
        return tweetsRepository.getTweet(params)
    }
}
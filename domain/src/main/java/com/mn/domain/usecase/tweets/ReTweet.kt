package com.mn.domain.usecase.tweets

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.UseCase
import javax.inject.Inject

class ReTweet @Inject constructor(private val tweetsRepository: TweetsRepository) :
    UseCase<Unit, String>() {
    override suspend fun run(params: String): Either<Failure, Unit> {
        return tweetsRepository.retweet(params)
    }
}
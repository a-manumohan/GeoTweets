package com.mn.domain.usecase.tweets

import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.UseCase
import javax.inject.Inject

class GetTweets @Inject constructor(private val tweetsRepository: TweetsRepository) :
    UseCase<List<Tweet>, Unit>() {
    override suspend fun run(params: Unit): Either<Failure, List<Tweet>> {
        return tweetsRepository.getTweets()
    }
}
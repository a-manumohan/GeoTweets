package com.mn.data.tweets

import com.mn.data.common.request
import com.mn.domain.NetworkHandler
import com.mn.domain.common.Either
import com.mn.domain.common.Failure
import com.mn.domain.usecase.tweets.Tweet
import com.mn.domain.usecase.tweets.TweetsRepository
import javax.inject.Inject

class TweetsRepositoryImpl @Inject constructor(
    private val tweetsApi: TweetsApi,
    private val networkHandler: NetworkHandler
) : TweetsRepository {
    private val tweetsMapper by lazy { TweetsMapper() }
    override suspend fun getTweets(): Either<Failure, List<Tweet>> {
        return request(tweetsApi.getHomeTimeLine()) {
            tweetsMapper(it)
        }
    }
}
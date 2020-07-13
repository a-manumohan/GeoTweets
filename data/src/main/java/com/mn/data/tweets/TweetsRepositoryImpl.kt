package com.mn.data.tweets

import com.mn.data.common.request
import com.mn.data.common.withNetwork
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
    private val tweetsMapper by lazy { TweetMapper() }

    override suspend fun getTweets(count: Int): Either<Failure, List<Tweet>> {
        return networkHandler.withNetwork {
            request(tweetsApi.getHomeTimeLine(count)) { tweets ->
                tweets.map { tweetsMapper(it) }
            }
        }
    }

    override suspend fun getTweet(id: String): Either<Failure, Tweet> {
        return networkHandler.withNetwork {
            request(tweetsApi.getTweet(id)) {
                tweetsMapper(it)
            }
        }
    }

    override suspend fun retweet(id: String): Either<Failure, Unit> {
        return networkHandler.withNetwork {
            request(tweetsApi.retweet(id)) {}
        }
    }

    override suspend fun unReTweet(id: String): Either<Failure, Unit> {
        return networkHandler.withNetwork {
            request(tweetsApi.unretweet(id)) {}
        }
    }
}
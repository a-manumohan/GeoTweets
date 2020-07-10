package com.mn.data.tweets

import retrofit2.Call

interface TweetsApi {

    fun getHomeTimeLine(): Call<TweetResponse>
}
package com.mn.data.tweets

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TweetsApi {

    @GET("1.1/statuses/home_timeline.json")
    @Headers("Connection: close")
    fun getHomeTimeLine(
        @Query("count") count: Int
    ): Call<List<TweetResponse>>
}
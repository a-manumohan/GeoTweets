package com.mn.data.tweets

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TweetsApi {

    @GET("1.1/statuses/home_timeline.json")
    @Headers("Connection: close")
    fun getHomeTimeLine(
        @Query("count") count: Int
    ): Call<List<TweetResponse>>

    @GET("1.1/statuses/show.json")
    @Headers("Connection: close")
    fun getTweet(
        @Query("id") id: String
    ): Call<TweetResponse>

    @POST("1.1/statuses/retweet/{id}.json")
    @Headers("Connection: close")
    fun retweet(@Path("id") id: String): Call<String>

    @POST("1.1/statuses/unretweet/{id}.json")
    @Headers("Connection: close")
    fun unretweet(@Path("id") id: String): Call<String>
}
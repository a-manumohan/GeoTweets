package com.mn.data.auth

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @POST("oauth/request_token")
    @FormUrlEncoded
    fun requestAuthToken(
        @Field("oauth_callback") url: String
    ): Call<String>

    @POST("oauth/access_token")
    @FormUrlEncoded
    fun requestAccessToken(
        @Field("oauth_token") token: String,
        @Field("oauth_verifier") verifier: String
    ): Call<String>
}
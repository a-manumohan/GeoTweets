package com.mn.data.auth

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

interface AuthApi {
    @POST("oauth/request_token")
    fun requestAuthToken(): Call<String>
}
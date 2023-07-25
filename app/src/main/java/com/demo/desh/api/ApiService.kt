package com.demo.desh.api

import com.demo.desh.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("oauth/authorize")
    fun getAuthCode(
        @Query("client_id") client_id: String,
        @Query("redirect_uri") redirect_uri: String,
        @Query("response_type") response_type: String,
        @Query("scope") scope: String
    )

    @POST("login")
    fun login(@Body user: User): Call<Long>
}
package com.example.desh.api

import com.example.desh.domain.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface RetrofitAPI {
    @POST("login")
    fun loginUser(@Body user: User): Call<User>?

    @PUT("update")
    fun updateUser(@Body user: User): Call<Long>
}
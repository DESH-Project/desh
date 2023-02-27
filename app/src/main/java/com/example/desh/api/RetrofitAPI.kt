package com.example.desh.api

import com.example.desh.domain.User
import retrofit2.Call
import retrofit2.http.Body

interface RetrofitAPI {
    fun loginUser(@Body user: User): Call<User>?
}
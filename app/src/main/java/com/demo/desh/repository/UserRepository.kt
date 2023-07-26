package com.demo.desh.repository

import com.demo.desh.api.RetrofitClient
import com.demo.desh.dto.User
import retrofit2.Call

class UserRepository {
    private val userService = RetrofitClient.userService

    suspend fun login(user: User): Call<Long> {
        return userService.login(user)
    }
}
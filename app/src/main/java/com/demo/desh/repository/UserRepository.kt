package com.demo.desh.repository

import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.Realty
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.ServiceList
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

class UserRepository {
    private val userService = RetrofitClient.userService

    fun login(user: User): Call<Long> {
        return userService.login(user)
    }

    suspend fun sendRealtyInfo(@Body realty: Realty): Response<Long> {
        return userService.sendRealtyInfo(realty)
    }

    suspend fun getServiceList(): Response<ServiceList> {
        return userService.getServiceList()
    }

    suspend fun getRecommendationAllInfo(): Response<RecommendInfo> {
        return userService.getRecommendationAllInfo()
    }

    suspend fun getRecommendationInfo(@Query("service") encodedServiceName: String): Response<RecommendInfo> {
        return userService.getRecommendationInfo(encodedServiceName)
    }
}
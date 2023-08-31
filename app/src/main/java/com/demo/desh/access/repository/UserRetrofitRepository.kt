package com.demo.desh.access.repository

import com.demo.desh.model.DistrictInfo
import com.demo.desh.util.RetrofitClient
import com.demo.desh.model.Realty
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.ServiceList
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.Response

class UserRetrofitRepository {
    private val userRetrofitDao = RetrofitClient.userRetrofitDao

    fun login(user: User): Call<Long> {
        return userRetrofitDao.login(user)
    }

    suspend fun sendRealtyInfo(realty: Realty): Response<Long> {
        return userRetrofitDao.sendRealtyInfo(realty)
    }

    suspend fun getServiceList(): Response<ServiceList> {
        return userRetrofitDao.getServiceList()
    }

    suspend fun getRecommendationAllInfo(): Response<RecommendInfo> {
        return userRetrofitDao.getRecommendationAllInfo()
    }

    suspend fun getRecommendationInfo(encodedServiceName: String): Response<RecommendInfo> {
        return userRetrofitDao.getRecommendationInfo(encodedServiceName)
    }

    suspend fun getDistrictInfo(encodedDistrictName: String): Response<DistrictInfo> {
        return userRetrofitDao.getStoreList(encodedDistrictName)
    }
}
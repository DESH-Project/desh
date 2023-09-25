package com.demo.desh.access.repository

import com.demo.desh.model.District
import com.demo.desh.model.Realty
import com.demo.desh.util.RetrofitClient
import com.demo.desh.model.RealtyCreationReq
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import retrofit2.Call
import retrofit2.Response

class UserRetrofitRepository {
    private val userRetrofitDao = RetrofitClient.userRetrofitDao

    fun login(user: User): Call<Long> {
        return userRetrofitDao.login(user)
    }

    suspend fun sendRealtyInfo(realty: RealtyCreationReq): Response<Long> {
        return userRetrofitDao.sendRealtyInfo(realty)
    }

    suspend fun getServiceList(): Response<ServerResponseObj<Map<String, List<String>>>> {
        return userRetrofitDao.getServiceList()
    }

    suspend fun getRecommendationAllInfo(): Response<ServerResponse<Recommend>> {
        return userRetrofitDao.getRecommendationAllInfo()
    }

    suspend fun getRecommendationInfo(encodedServiceName: String): Response<ServerResponse<Recommend>> {
        return userRetrofitDao.getRecommendationInfo(encodedServiceName)
    }

    suspend fun getDistrictInfo(encodedDistrictName: String): Response<ServerResponse<District>> {
        return userRetrofitDao.getStoreList(encodedDistrictName)
    }

    suspend fun getRealtyDetail(realtyId: Long, userId: Long): Response<ServerResponse<Realty>> {
        return userRetrofitDao.getRealtyDetail(realtyId, userId)
    }

    suspend fun getIntroImage() : Response<ServerResponse<String>> {
        return userRetrofitDao.getIntroImage()
    }
}
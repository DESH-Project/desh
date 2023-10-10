package com.demo.desh.access.repository

import android.util.Log
import com.demo.desh.access.dao.RetrofitClient
import com.demo.desh.model.District
import com.demo.desh.model.IntroStore
import com.demo.desh.model.Realty
import com.demo.desh.model.RealtyCreationReq
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import retrofit2.Response

class UserRetrofitRepository {
    companion object {
        private const val TAG = "UserRetrofitRepository"

        private val userRetrofitDao = RetrofitClient.userRetrofitDao

        private fun logging(method: String, res: Any) {
            Log.e(TAG, "method = $method, res = $res")
        }
    }

    suspend fun login(user: User): Response<Long> =
        userRetrofitDao
            .login(user)
            .also { logging("login", it) }

    suspend fun sendRealtyInfo(realty: RealtyCreationReq): Response<Long> =
        userRetrofitDao
            .sendRealtyInfo(realty)
            .also { logging("sendRealtyInfo", it) }

    suspend fun getServiceList(): Response<ServerResponseObj<Map<String, List<String>>>> =
        userRetrofitDao
            .getServiceList()
            .also { logging("getServiceList", it) }

    suspend fun getRecommendationAllInfo(): Response<ServerResponse<Recommend>> =
        userRetrofitDao
            .getRecommendationAllInfo()
            .also { logging("getRecommendationAllInfo", it) }

    suspend fun getRecommendationInfo(encodedServiceName: String): Response<ServerResponse<Recommend>> =
        userRetrofitDao
            .getRecommendationInfo(encodedServiceName)
            .also { logging("getRecommendationInfo", it) }

    suspend fun getDistrictInfo(encodedDistrictName: String): Response<ServerResponse<District>> =
        userRetrofitDao
            .getStoreList(encodedDistrictName)
            .also { logging("getDistrictInfo", it) }

    suspend fun getRealtyDetail(realtyId: Long, userId: Long): Response<ServerResponse<Realty>> =
        userRetrofitDao
            .getRealtyDetail(realtyId, userId)
            .also { logging("getRealtyDetail", it) }


    suspend fun getIntroImage(): Response<ServerResponse<String>> =
        userRetrofitDao
            .getIntroImage()
            .also { logging("getIntroImage", it) }

    suspend fun getIntroStore(): Response<ServerResponse<IntroStore>> =
        userRetrofitDao
            .getIntroStore()
            .also { logging("getIntroStore", it) }

}
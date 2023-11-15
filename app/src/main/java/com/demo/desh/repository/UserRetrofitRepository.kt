package com.demo.desh.repository

import android.util.Log
import com.demo.desh.access.RoomAccessDao
import com.demo.desh.access.UserRetrofitDao
import com.demo.desh.model.Realty
import com.demo.desh.model.RealtyCreationReq
import com.demo.desh.model.RealtyPreview
import com.demo.desh.model.RecommendDistrict
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRetrofitRepository @Inject constructor(
    private val userRetrofitDao: UserRetrofitDao,
    private val roomAccessDao: RoomAccessDao
) {
    companion object {
        private const val TAG = "UserRetrofitRepository"

        private fun logging(method: String, res: Any) {
            Log.e(TAG, "method = $method, res = $res")
        }
    }

    suspend fun login(user: User): Response<Long> {
        return userRetrofitDao
            .login(user)
            .also { logging("login", it) }
    }

    suspend fun getUserInfo(userId: Long) : Response<ServerResponseObj<User>> {
        return userRetrofitDao
            .getUserInfo(userId)
            .also { logging("getUserInfo", it) }
    }

    suspend fun getNearbyStoreList(encodedDistrictName: String): Response<ServerResponse<RealtyPreview>> {
        return userRetrofitDao
            .getStoreList(encodedDistrictName)
            .also { logging("getNearbyStoreList", it) }
    }

    suspend fun getUserPickedStoreList(userId: Long) : Response<ServerResponse<RealtyPreview>> {
        return userRetrofitDao
            .getPickedStoreList(userId)
            .also { logging("getUserPickedStoreList", it) }
    }

    suspend fun getUserRegisterStoreList(userId: Long) : Response<ServerResponse<RealtyPreview>> {
        return userRetrofitDao
            .getRegisterStoreList(userId)
            .also { logging("getUserRegisterStoreList", it) }
    }

    // -----------

    suspend fun sendRealtyInfo(realty: RealtyCreationReq): Response<Long> = withContext(Dispatchers.IO) {
        userRetrofitDao
            .sendRealtyInfo(realty)
            .also { logging("sendRealtyInfo", it) }
    }

    suspend fun getServiceList(): Response<ServerResponseObj<Map<String, List<String>>>> = withContext(Dispatchers.IO) {
        userRetrofitDao
            .getServiceList()
            .also { logging("getServiceList", it) }
    }

    suspend fun getRecommendationAllInfo(): Response<ServerResponse<RecommendDistrict>> = withContext(Dispatchers.IO) {
        userRetrofitDao
            .getRecommendationAllInfo()
            .also { logging("getRecommendationAllInfo", it) }
    }

    suspend fun getRecommendationInfo(encodedServiceName: String): Response<ServerResponse<RecommendDistrict>> = withContext(Dispatchers.IO) {
        userRetrofitDao
            .getRecommendationInfo(encodedServiceName)
            .also { logging("getRecommendationInfo", it) }
    }

    suspend fun getRealtyDetail(realtyId: Long, userId: Long): Response<ServerResponse<Realty>> = withContext(Dispatchers.IO) {
        userRetrofitDao
            .getRealtyDetail(realtyId, userId)
            .also { logging("getRealtyDetail", it) }
    }

    suspend fun getIntroImage(): Response<ServerResponse<String>> {
        return userRetrofitDao
            .getIntroImage()
            .also { logging("getIntroImage", it) }
    }

    suspend fun sendPickedStore(userId: Long, realtyId: Long): Response<ServerResponse<Int>> = withContext(Dispatchers.IO) {
        userRetrofitDao
            .sendPickedStore(userId, realtyId)
            .also { logging("sendPickedStore", it) }
    }

}
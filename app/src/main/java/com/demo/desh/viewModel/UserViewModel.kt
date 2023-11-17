package com.demo.desh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.repository.UserRetrofitRepository
import com.demo.desh.model.Realty
import com.demo.desh.model.RealtyPreview
import com.demo.desh.model.RecommendDistrict
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRetrofitRepository: UserRetrofitRepository
) : ViewModel() {
    companion object {
        private const val DEFAULT_SERVICE_NAME = "전체"
        private const val DEFAULT_ENCODE_TYPE = "UTF-8"
    }

    var open = MutableLiveData(false)


    /* 로그인 화면 미리보기 이미지 */
    private val _previewImages = MutableLiveData<List<String>>()
    val previewImages : LiveData<List<String>> get() = _previewImages

    fun loadPreviewImages() {
        open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { userRetrofitRepository.getIntroImage() }.await()

            if (def.isSuccessful) {
                val images = def.body()?.data
                _previewImages.value = images
                open.value = false
            }
        }
    }

    /* 카카오 소셜 로그인 & 서버 전송 */
    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user

    fun fetchUser(user: User) {
        open.value = true

        viewModelScope.launch {
            runBlocking {
                val res = async(Dispatchers.IO) { userRetrofitRepository.login(user) }.await()

                if (res.isSuccessful) {
                    _user.value = User(
                        userId = res.body(),
                        nickname = user.nickname,
                        email = user.email,
                        profileImageUrl = user.profileImageUrl,
                        description = user.description
                    )
                    open.value = false
                }
            }
        }
    }

    /* 유저 상세정보 조회 */
    private val _targetUser : MutableLiveData<User> = MutableLiveData()
    val targetUser : LiveData<User> get() = _targetUser

    fun getUserInfo(userId: Long) {
        open.value = true
        viewModelScope.launch {
            val def = async(Dispatchers.IO) { userRetrofitRepository.getUserInfo(userId) }.await()

            if (def.isSuccessful) {
                val result = def.body()!!.data
                _targetUser.value = result
            }
        }
        open.value = false
    }



    /* 유저가 찜한 상가 목록 리스트 가져오기 */
    private var _userPickedStore = MutableLiveData<List<RealtyPreview>>()
    val userPickedStore : LiveData<List<RealtyPreview>> get() = _userPickedStore

    fun getUserPickedStore(userId: Long) {
        open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { userRetrofitRepository.getUserPickedStoreList(userId) }.await()

            if (def.isSuccessful) {
                val res = def.body()!!.data
                _userPickedStore.value = res

                open.value = false
            }
        }
    }

    /* 유저가 등록한 상가 목록 리스트 가져오기 */
    private var _userRegStore = MutableLiveData<List<RealtyPreview>>()
    val userRegStore : LiveData<List<RealtyPreview>> get() = _userRegStore

    fun getUserRegStore(userId: Long) {
        open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { userRetrofitRepository.getUserRegisterStoreList(userId) }.await()

            if (def.isSuccessful) {
                val res = def.body()!!.data
                _userRegStore.value = res

                open.value = false
            }
        }
    }

    /* 상가 건물 찜하기 */
    private var _starCount = MutableLiveData<ServerResponse<Int>>()
    val starCount: LiveData<ServerResponse<Int>> get() = _starCount

    fun sendPickedStore(userId: Long, realtyId: Long) {
        viewModelScope.launch {
            val res = userRetrofitRepository.sendPickedStore(userId, realtyId)

            if (res.isSuccessful) {
                _starCount.value = res.body()!!
            }
        }
    }

    /* 서비스 업종에 따른 추천 상권 목록 가져오기 */
    private var _recommendDistrictList = MutableLiveData<List<RecommendDistrict>>()
    val recommendDistrictList: LiveData<List<RecommendDistrict>> get() = _recommendDistrictList

    fun loadRecommendDistrict(serviceName: String = DEFAULT_SERVICE_NAME) {
        viewModelScope.launch {
            val def = async(Dispatchers.IO) {
                if (serviceName == DEFAULT_SERVICE_NAME) userRetrofitRepository.getRecommendationAllInfo()
                else userRetrofitRepository.getRecommendationInfo(URLEncoder.encode(serviceName, DEFAULT_ENCODE_TYPE))
            }.await()

            if (def.isSuccessful) {
                val body = def.body()!!
                _recommendDistrictList.value = body.data
            }
        }
    }

    /* 서비스(업종) 리스트 가져오기 */
    private val _serviceList = MutableLiveData<Map<String, List<String>>>()
    val serviceList: LiveData<Map<String, List<String>>> get() = _serviceList

    fun fetchServiceList() {
        viewModelScope.launch {
            val res = userRetrofitRepository.getServiceList()

            if (res.isSuccessful) {
                val body = res.body()!!
                _serviceList.value = body.data
            }
        }
    }

    /* 상권 인근 상가 리스트 가져오기 */
    private var _nearbyStoreList = MutableLiveData<List<RealtyPreview>>()
    val nearbyStoreList : LiveData<List<RealtyPreview>> get() = _nearbyStoreList

    fun fetchNearbyStores(districtName: String) {
        viewModelScope.launch {
            val def = async(Dispatchers.IO) {
                val encodedName = URLEncoder.encode(districtName, DEFAULT_ENCODE_TYPE)
                userRetrofitRepository.getNearbyStoreList(encodedName)
            }.await()

            if (def.isSuccessful) {
                val res = def.body()!!
                _nearbyStoreList.value = res.data
            }
        }
    }

    /* 상가 디테일 정보 가져오기 */
    private val _realtyDetail = MutableLiveData<Realty>()
    val realtyDetail: LiveData<Realty> get() = _realtyDetail

    fun fetchRealtyDetail(realtyId: Long, userId: Long) {
        open.value = true
        viewModelScope.launch {
            val res = userRetrofitRepository.getRealtyDetail(realtyId, userId)

            if (res.isSuccessful) {
                val body = res.body()!!
                _realtyDetail.value = body.data
            }
        }
        open.value = false
    }
}
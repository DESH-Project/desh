package com.demo.desh.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.access.UserRetrofitRepository
import com.demo.desh.model.District
import com.demo.desh.model.Realty
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRetrofitRepository: UserRetrofitRepository
) : ViewModel() {
    companion object {
        private const val DEFAULT_SERVICE_NAME = "전체"
        private const val DEFAULT_ENCODE_TYPE = "UTF-8"
    }

    private var _open = MutableLiveData<Boolean>(false)
    val open : LiveData<Boolean> get() = _open


    /* 로그인 화면 미리보기 이미지 */
    private val _previewImages = MutableLiveData<List<String>>()
    val previewImages : LiveData<List<String>> get() = _previewImages

    fun loadPreviewImages() {
        _open.value = true

        viewModelScope.launch {
            val def = async(Dispatchers.IO) { userRetrofitRepository.getIntroImage() }.await()

            if (def.isSuccessful) {
                val images = def.body()?.data
                _previewImages.value = images
                _open.value = false
            }
        }
    }

    /* 카카오 소셜 로그인 & 서버 전송 */
    private val _user : MutableLiveData<User> = MutableLiveData()
    val user : LiveData<User> get() = _user

    fun fetchUser(user: User) {
        _open.value = true

        runBlocking {
            val res = async(Dispatchers.IO) { userRetrofitRepository.login(user) }.await()

            if (res.isSuccessful) {
                user.id = res.body()
                _user.value = user
                _open.value = false
            }
        }
    }


    /* 유저 상세정보 조회 */
    private val _targetUser : MutableLiveData<User> = MutableLiveData()
    val targetUser : LiveData<User> get() = _targetUser

    fun getUserInfo(userId: Long) =
        viewModelScope.launch {
            val res = userRetrofitRepository.getUserInfo(userId)

            if (res.isSuccessful) {
                val result = res.body()!!.data
                _targetUser.value = result
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


    private val _recommendInfo = MutableLiveData<ServerResponse<Recommend>>()
    val recommendInfo: LiveData<ServerResponse<Recommend>> get() = _recommendInfo
    private val _selectedServiceName = MutableLiveData<String>(DEFAULT_SERVICE_NAME)
    val selectedServiceName : LiveData<String> get() = _selectedServiceName

    fun fetchMapView(serviceName: String = DEFAULT_SERVICE_NAME) {
        viewModelScope.launch {
            _selectedServiceName.value = serviceName

            val res =
                if (serviceName == DEFAULT_SERVICE_NAME) userRetrofitRepository.getRecommendationAllInfo()
                else userRetrofitRepository.getRecommendationInfo(URLEncoder.encode(serviceName, DEFAULT_ENCODE_TYPE))

            if (res.isSuccessful) {
                val body = res.body()!!
                _recommendInfo.value = body
            }
        }
    }

    private val _serviceList = MutableLiveData<ServerResponseObj<Map<String, List<String>>>>()
    val serviceList: LiveData<ServerResponseObj<Map<String, List<String>>>> get() = _serviceList

    fun fetchServiceList() {
        viewModelScope.launch {
            val res = userRetrofitRepository.getServiceList()

            if (res.isSuccessful) {
                val body = res.body()!!
                _serviceList.value = body
            }
        }
    }

    private val _districtInfo = MutableLiveData<ServerResponse<District>>()
    val districtInfo: LiveData<ServerResponse<District>> get() = _districtInfo

    fun fetchDistrictInfoList(districtName: String) {
        viewModelScope.launch {
            val encodedDistrictName = URLEncoder.encode(districtName, DEFAULT_ENCODE_TYPE)
            val res = userRetrofitRepository.getDistrictInfo(encodedDistrictName)

            if (res.isSuccessful) {
                val body = res.body()!!
                if (body.size == 0) _districtInfo.value = randomDistrictSampleList()
                else _districtInfo.value = body
            }
        }
    }

    private val _realtyDetail = MutableLiveData<ServerResponse<Realty>>()
    val realtyDetail: LiveData<ServerResponse<Realty>> get() = _realtyDetail

    fun fetchRealtyDetail(realtyId: Long, userId: Long) {
        viewModelScope.launch {
            val res = userRetrofitRepository.getRealtyDetail(realtyId, userId)

            if (res.isSuccessful) {
                val body = res.body()!!
                _realtyDetail.value = body
            }

            else {
                val sampleRealtyInfo = Realty(
                    id = realtyId,
                    name = "건물 이름입니다.",
                    price = 12.7,
                    address = "서울시 노원구 상계 1동",
                    pyung = 1234,
                    squareMeter = 33.3,
                    image = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/1/%EC%A7%84%EC%A3%BC+%EA%B7%80%EA%B1%B8%EC%9D%B4%EB%A5%BC+%ED%95%9C+%EC%86%8C%EB%85%802.png",
                    nearby = "근처 상권입니다.",
                    userId = userId
                )

                val sample = ServerResponse(1, listOf(sampleRealtyInfo))

                _realtyDetail.value = sample
            }
        }
    }

    private fun randomDistrictSampleList() : ServerResponse<District> {
        val random = Random.Default
        val size = random.nextInt(20, 30)
        val list = mutableListOf<District>()

        val randomPhotoUrl = listOf(
            "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/copernico-p_kICQCOM4s-unsplash.jpg",
            "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/damir-kopezhanov-luseu9GtYzM-unsplash.jpg",
            "https://ddakdae-s3-bucket.s3.ap-northeast-2.amazonaws.com/flow_photo/jose-losada-DyFjxmHt3Es-unsplash.jpg"
        )

        for (i in 0 until size) {
            val pick = random.nextInt(0, randomPhotoUrl.size)
            val district = District(
                id = (i + 1).toLong(),
                address = UUID.randomUUID().toString(),
                image = randomPhotoUrl[pick],
                price = random.nextDouble(1.0, 1000.0)
            )

            list.add(district)
        }

        return ServerResponse(size, list)
    }
}
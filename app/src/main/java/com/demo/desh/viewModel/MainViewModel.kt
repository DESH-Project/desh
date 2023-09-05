package com.demo.desh.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.access.repository.UserRetrofitRepository
import com.demo.desh.model.District
import com.demo.desh.model.Realty
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.util.MapViewManager
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapView
import java.net.URLEncoder
import java.util.UUID
import kotlin.random.Random

class MainViewModel(private val userRetrofitRepository: UserRetrofitRepository) : ViewModel() {
    companion object {
        private const val DEFAULT_SERVICE_NAME = "전체"
        private const val DEFAULT_ENCODE_TYPE = "UTF-8"
    }

    private val _mapView = MutableLiveData<(context: Context) -> MapView>()
    val mapView: LiveData<(context: Context) -> MapView> get() = _mapView

    private val _recommendInfo = MutableLiveData<ServerResponse<Recommend>>()
    val recommendInfo: LiveData<ServerResponse<Recommend>> get() = _recommendInfo

    private val _infoText = MutableLiveData<String>()
    val infoText: LiveData<String> get() = _infoText

    fun fetchMapView(serviceName: String = DEFAULT_SERVICE_NAME) {
        viewModelScope.launch {
            val res =
                if (serviceName == DEFAULT_SERVICE_NAME) userRetrofitRepository.getRecommendationAllInfo()
                else userRetrofitRepository.getRecommendationInfo(URLEncoder.encode(serviceName, DEFAULT_ENCODE_TYPE))

            Log.e("MapScreen : fetchMapView()", "res = $res, res body = ${res.body()?.data}")

            if (res.isSuccessful) {
                val body = res.body()!!
                _mapView.value = MapViewManager.createMapView(body)
                _recommendInfo.value = body
                _infoText.value = if (serviceName == DEFAULT_SERVICE_NAME) "전체" else serviceName
            }
        }
    }

    private val _serviceList = MutableLiveData<ServerResponse<String>>()
    val serviceList: LiveData<ServerResponse<String>> get() = _serviceList

    fun fetchServiceList() {
        viewModelScope.launch {
            val res = userRetrofitRepository.getServiceList()
            Log.e("MapScreen : fetchServiceList()", "res = $res, res body = ${res.body()}")

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

            Log.e("MapScreen : fetchDistrictInfoList()", "res = ${res.body()}")

            _districtInfo.value = randomDistrictSampleList()

            /*
            if (res.isSuccessful) {
                val body = res.body()!!

                if (body.list.isEmpty()) {
                    val sample = District(
                        id = 1L,
                        address = "서울시 노원구 상계 1동",
                        image = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/1/%EC%A7%84%EC%A3%BC+%EA%B7%80%EA%B1%B8%EC%9D%B4%EB%A5%BC+%ED%95%9C+%EC%86%8C%EB%85%802.png",
                        price = 12.7
                    )

                    body.list.add(sample)
                }

                _districtInfo.value = body
            }
            */
        }
    }

    private val _realtyDetail = MutableLiveData<ServerResponse<Realty>>()
    val realtyDetail: LiveData<ServerResponse<Realty>> get() = _realtyDetail

    fun fetchRealtyDetail(realtyId: Long, userId: Long) {
        viewModelScope.launch {
            val res = userRetrofitRepository.getRealtyDetail(realtyId, userId)
            Log.e("MainViewModel.fetchRealtyDetail()", "res = ${res.body()}")

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

        for (i in 0 until size) {
            val district = District(
                id = (i + 1).toLong(),
                address = UUID.randomUUID().toString(),
                image = "https://artfolio-bucket.s3.ap-northeast-2.amazonaws.com/static/artPiece/1/%EC%A7%84%EC%A3%BC+%EA%B7%80%EA%B1%B8%EC%9D%B4%EB%A5%BC+%ED%95%9C+%EC%86%8C%EB%85%802.png",
                price = random.nextDouble(1.0, 1000.0)
            )

            list.add(district)
        }

        return ServerResponse(size, list)
    }
}
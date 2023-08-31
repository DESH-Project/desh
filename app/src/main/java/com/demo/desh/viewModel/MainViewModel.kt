package com.demo.desh.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.ServiceList
import com.demo.desh.access.repository.UserRetrofitRepository
import com.demo.desh.model.DistrictInfo
import com.demo.desh.util.MapViewManager
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainViewModel(private val userRetrofitRepository: UserRetrofitRepository) : ViewModel() {
    companion object {
        private const val DEFAULT_SERVICE_NAME = "전체"
        private const val DEFAULT_ENCODE_TYPE = "UTF-8"
    }

    private val _mapView = MutableLiveData<(context: Context) -> MapView>()
    val mapView: LiveData<(context: Context) -> MapView> get() = _mapView

    private val _recommendInfo = MutableLiveData<RecommendInfo>()
    val recommendInfo: LiveData<RecommendInfo> get() = _recommendInfo

    private val _infoText = MutableLiveData<String>()
    val infoText: LiveData<String> get() = _infoText

    fun fetchMapView(serviceName: String = DEFAULT_SERVICE_NAME) {
        viewModelScope.launch {
            val res =
                if (serviceName == DEFAULT_SERVICE_NAME) userRetrofitRepository.getRecommendationAllInfo()
                else userRetrofitRepository.getRecommendationInfo(URLEncoder.encode(serviceName, DEFAULT_ENCODE_TYPE))

            Log.e("MapScreen : fetchMapView()", "res = ${res.body()?.list}")

            if (res.isSuccessful) {
                val body = res.body()!!
                _mapView.value = MapViewManager.createMapView(body)
                _recommendInfo.value = body
                _infoText.value = if (serviceName == DEFAULT_SERVICE_NAME) "전체" else serviceName
            }
        }
    }

    private val _serviceList = MutableLiveData<ServiceList>()
    val serviceList: LiveData<ServiceList> get() = _serviceList

    fun fetchServiceList() {
        viewModelScope.launch {
            val res = userRetrofitRepository.getServiceList()
            Log.e("MapScreen : fetchServiceList()", "res = $res")

            if (res.isSuccessful) {
                val body = res.body()!!
                _serviceList.value = body
            }
        }
    }

    private val _districtInfo = MutableLiveData<DistrictInfo>()
    val districtInfo: LiveData<DistrictInfo> get() = _districtInfo

    fun fetchDistrictInfoList(districtName: String) {
        viewModelScope.launch {
            val encodedDistrictName = URLEncoder.encode(districtName, DEFAULT_ENCODE_TYPE)
            val res = userRetrofitRepository.getDistrictInfo(encodedDistrictName)

            if (res.isSuccessful) {
                val body = res.body()!!
                _districtInfo.value = body
            }
        }
    }
}
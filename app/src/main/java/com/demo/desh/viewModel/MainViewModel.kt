package com.demo.desh.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.desh.util.MapCreator
import net.daum.mf.map.api.MapView

class MainViewModel : ViewModel() {
    private val _mapView = MutableLiveData(MapCreator.getCommonMapView())
    val mapView: LiveData<(context: Context) -> MapView> get() = _mapView

    fun updateMapView(serviceName: String) {
        _mapView.postValue(MapCreator.getCommonMapView(serviceName))
    }
}
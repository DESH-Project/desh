package com.demo.desh.util

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.demo.desh.MainActivity
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.MapViewInfo
import java.lang.Exception

object MapViewManager {
    /*
    fun onMapViewUpdate(mv: MapView, recommendInfo: ServerResponse<Recommend>?, markerEventListener: MainActivity.MarkerEventListener) {
        mv.removeAllCircles()
        mv.removeAllPOIItems()

        // mv.setCalloutBalloonAdapter(CustomBalloonAdapter())
        mv.setPOIItemEventListener(markerEventListener)

        recommendInfo?.data?.forEachIndexed { idx, it ->
            Log.e("MapViewManager", "${it.predict}, ${it.predict.formatDecimalSeparator()}")

            val marker = MapPOIItem()
                .apply {
                    itemName = it.district
                    mapPoint = MapPoint.mapPointWithGeoCoord(it.lat, it.lng)
                    markerType = MapPOIItem.MarkerType.RedPin
                    selectedMarkerType = MapPOIItem.MarkerType.BluePin
                    tag = idx
                    isDraggable = false
                }

            mv.addPOIItem(marker)

            val circle = MapCircle(
                MapPoint.mapPointWithGeoCoord(it.lat, it.lng),
                500,
                Color(196, 128, 128, 128).toArgb(),
                Color(196, 128, 128, 128).toArgb()
            )

            mv.addCircle(circle)
        }

        mv.fitMapViewAreaToShowAllPOIItems()
        mv.isSelected = true
    }

     */
    fun createMapView(context: Context, recommendInfo: ServerResponse<Recommend>?) : MapView {
        val mv = MapView(context)

        mv.start(
            object : MapLifeCycleCallback() {
                // 지도 API가 정상적으로 종료될 때 호출
                override fun onMapDestroy() {
                    TODO("Not yet implemented")
                }

                // 인증 실패 및 지도 사용 중 에러 발생시 호출
                override fun onMapError(error: Exception?) {
                    TODO("Not yet implemented")
                }
            },

            object : KakaoMapReadyCallback() {
                // 인증 후 API가 정상적으로 실행될 때 호출
                override fun onMapReady(kakaoMap: KakaoMap) {
                    Log.e("Map", "지도 시작")
                }

                // 지도 시작 시 확대/축소 줌 레벨 설정
                override fun getZoomLevel(): Int {
                    return super.getZoomLevel()
                }

                // 지도 시작 시 위치 좌표 설정
                override fun getPosition(): LatLng {
                    return LatLng.from(37.486960, 127.115587)
                }

                // 지도 시작 시 App 및 MapType 설정
                override fun getMapViewInfo(): MapViewInfo {
                    return super.getMapViewInfo()
                }

                // KakaoMap의 고유한 이름 설정
                override fun getViewName(): String {
                    return super.getViewName()
                }

                // KakaoMap의 tag를 설정
                override fun getTag(): Any {
                    return super.getTag()
                }

                // 지도 시작 시 visible 여부 설정
                override fun isVisible(): Boolean {
                    return true
                }
            }
        )

        /*
        val markers = if (recommendInfo == null) getMapItems(null) else getMapItems(recommendInfo)
        return makeMapView(markers)
        */

        return mv
    }

    /*
    private fun makeMapView(markers: List<MapPOIItem>) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)
            markers.forEach { mv.addPOIItem(it) }
            mv
        }
    }

    private fun getMapItems(recommendInfo: ServerResponse<Recommend>?) : List<MapPOIItem> {
        val markers = mutableListOf<MapPOIItem>()

        if (recommendInfo?.data?.isNotEmpty() == true) {
            val list = recommendInfo.data

            list.forEach {
                val marker = MapPOIItem()
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(it.lat, it.lng)
                marker.markerType = MapPOIItem.MarkerType.RedPin
                marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin
                marker.itemName = it.district

                markers.add(marker)
            }
        }

        return markers
    }
    private fun Long.formatDecimalSeparator(): String {
        return toString()
    }
     */
}

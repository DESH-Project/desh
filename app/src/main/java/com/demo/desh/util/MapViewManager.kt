package com.demo.desh.util

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.demo.desh.R
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.MapViewInfo
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.shape.DotPoints
import com.kakao.vectormap.shape.PolygonOptions
import com.kakao.vectormap.shape.PolygonStyles
import com.kakao.vectormap.shape.PolygonStylesSet
import java.lang.Exception

object MapViewManager {
    fun labelingOnMapView(mapView: MapView, recommendInfo: ServerResponse<Recommend>?) {
        mapView.removeAllViews()

        mapView.start(
            object : MapLifeCycleCallback() {
                // 지도 API가 정상적으로 종료될 때 호출
                override fun onMapDestroy() {
                    mapView.pause()
                }

                // 인증 실패 및 지도 사용 중 에러 발생시 호출
                override fun onMapError(error: Exception?) {
                    TODO("Not yet implemented")
                }

                override fun onMapResumed() {
                    super.onMapResumed()
                }

                override fun onMapPaused() {
                    super.onMapPaused()
                }
            },

            object : KakaoMapReadyCallback() {
                // 인증 후 API가 정상적으로 실행될 때 호출
                override fun onMapReady(kakaoMap: KakaoMap) {
                    val labelManager = kakaoMap.labelManager
                    val layer = labelManager?.layer
                    val styles =
                        labelManager?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.ic_launcher_background)))

                    val dimScreenLayer = kakaoMap.dimScreenManager?.dimScreenLayer
                    dimScreenLayer?.apply {
                        setVisible(true)
                        setColor(Color(0x22FFFFFF).toArgb())
                    }


                    recommendInfo?.data?.forEachIndexed { _, item ->
                        val options = LabelOptions.from(LatLng.from(item.lat, item.lng))
                            .setStyles(styles)
                            .setClickable(true)

                        val polygonOptions = PolygonOptions
                            .from(DotPoints.fromCircle(LatLng.from(item.lat, item.lng), 1200f))
                            .setStylesSet(PolygonStylesSet.from(PolygonStyles.from(Color.Transparent.toArgb())))

                        layer?.addLabel(options)
                        dimScreenLayer
                            ?.addPolygon(polygonOptions)
                    }

                    kakaoMap.setOnPoiClickListener { kakaoMap, position, layerId, poiId ->
                        kakaoMap.moveCamera(
                            CameraUpdateFactory.zoomIn()
                        )
                    }
                }
            }
        )
    }

    fun createMapView(context: Context) : MapView {
        val mv = MapView(context)

        mv.start(
            object : MapLifeCycleCallback() {
                // 지도 API가 정상적으로 종료될 때 호출
                override fun onMapDestroy() {
                }

                // 인증 실패 및 지도 사용 중 에러 발생시 호출
                override fun onMapError(error: Exception?) {
                }
            },

            object : KakaoMapReadyCallback() {
                // 인증 후 API가 정상적으로 실행될 때 호출
                override fun onMapReady(kakaoMap: KakaoMap) {
                    kakaoMap.setOnPoiClickListener { kakaoMap, position, layerId, poiId ->
                        kakaoMap.moveCamera(CameraUpdateFactory.zoomIn())
                    }
                }

                // 지도 시작 시 확대/축소 줌 레벨 설정
                override fun getZoomLevel(): Int {
                    return super.getZoomLevel()
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
}

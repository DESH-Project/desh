package com.demo.desh.util

import android.content.Context
import com.demo.desh.R
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles

object MapViewManager {
    fun createMapView(context: Context, loc: LatLng) : MapView {
        val mv = MapView(context)

        mv.start(
            object : KakaoMapReadyCallback() {
                // 인증 후 API가 정상적으로 실행될 때 호출
                override fun onMapReady(kakaoMap: KakaoMap) {
                    CameraUpdateFactory.newCenterPosition(loc)
                    CameraUpdateFactory.zoomTo(16)

                    val styles = kakaoMap
                        .labelManager!!
                        .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.marker)))

                    val options = LabelOptions.from(loc).setStyles(styles)

                    val layer = kakaoMap.labelManager!!.layer
                    val label = layer.addLabel(options)
                    label.show(true)
                }

                // 지도 시작 시 확대/축소 줌 레벨 설정
                override fun getZoomLevel(): Int {
                    return 14
                }
            }
        )

        return mv
    }
}
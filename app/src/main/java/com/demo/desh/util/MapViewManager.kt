package com.demo.desh.util

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.demo.desh.R
import com.demo.desh.model.District
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
import com.kakao.vectormap.shape.PolygonStyle
import com.kakao.vectormap.shape.PolygonStyles
import com.kakao.vectormap.shape.PolygonStylesSet
import java.lang.Exception

@Composable
fun rememberVector(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "vector",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(480f, -120f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(400f, -200f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(480f, -280f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(560f, -200f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(480f, -120f)
                close()
                moveToRelative(-80f, -240f)
                verticalLineToRelative(-480f)
                horizontalLineToRelative(160f)
                verticalLineToRelative(480f)
                horizontalLineTo(400f)
                close()
            }
        }.build()
    }
}

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
            },

            object : KakaoMapReadyCallback() {
                // 인증 후 API가 정상적으로 실행될 때 호출
                override fun onMapReady(kakaoMap: KakaoMap) {
                    val labelManager = kakaoMap.labelManager
                    val layer = labelManager?.layer
                    val styles =
                        labelManager?.addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.chamomile)))

                    val dimScreenLayer = kakaoMap.dimScreenManager?.dimScreenLayer
                    dimScreenLayer?.apply {
                        setVisible(true)
                        setColor(Color(0x22FFFFFF).toArgb())
                    }

                    Log.e("KakaoMapReadyCallback onMapReady", "recommendInfo : $recommendInfo")

                    recommendInfo?.data?.forEachIndexed { _, item ->
                        Log.e("KakaoMap", "item : $item")
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
                    mv.pause()
                }

                // 인증 실패 및 지도 사용 중 에러 발생시 호출
                override fun onMapError(error: Exception?) {
                    TODO("Not yet implemented")
                }
            },

            object : KakaoMapReadyCallback() {
                // 인증 후 API가 정상적으로 실행될 때 호출
                override fun onMapReady(kakaoMap: KakaoMap) {

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

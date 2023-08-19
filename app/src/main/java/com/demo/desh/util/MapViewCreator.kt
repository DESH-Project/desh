package com.demo.desh.util

import android.content.Context
import android.graphics.Color.argb
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapView

object MapViewCreator {
    fun createMapView(recommendInfo: RecommendInfo? = RecommendInfo(1, listOf(
        Recommend(
            lat = 37.53737528,
            lng = 127.00557633,
            service = "DEFAULT",
            district = "???",
            predict = 1
    )))) : (context: Context) -> MapView {
        val circles = getMapItems(recommendInfo)
        return makeMapView(circles)
    }

    private fun makeMapView(circles: List<MapCircle>) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)
            val mapPointBoundsArray = mutableListOf<MapPointBounds>()

            circles.forEach { circle ->
                mv.addCircle(circle)
                mapPointBoundsArray.add(circle.bound)
            }

            val mapPointBounds = MapPointBounds(mapPointBoundsArray.toTypedArray())
            val padding = 150

            mv.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))
            mv
        }
    }

    /* recommendInfo -> Circle 생성 */
    private fun getMapItems(recommendInfo: RecommendInfo?) : List<MapCircle> {
        val circles = mutableListOf<MapCircle>()
        val list = recommendInfo?.list

        list?.forEachIndexed { _, res ->
            val circle = MapCircle(
                MapPoint.mapPointWithGeoCoord(res.lat, res.lng),
                1000,
                argb(255, 255, 255, 0),
                argb(128, 128, 128, 128)
            )

            circles.add(circle)
        }

        return circles
    }
}
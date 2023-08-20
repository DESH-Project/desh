package com.demo.desh.util

import android.content.Context
import android.graphics.Color
import android.graphics.Color.argb
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.random.Random

object MapViewCreator {
    fun createMapView(recommendInfo: RecommendInfo?) : (context: Context) -> MapView {
        val circles = if (recommendInfo == null) getMapItems() else getMapItems(recommendInfo.list)
        return makeMapView(circles)
    }

    private fun makeMapView(circles: List<MapCircle>) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)

            circles.forEach { circle ->
                mv.addCircle(circle)
            }

            mv
        }
    }

    /* recommendInfo -> Circle 생성 */
    private fun getMapItems(list: List<Recommend> = listOf()) : List<MapCircle> {
        val circles = mutableListOf<MapCircle>()

        if (list.isNotEmpty()) {
            list.forEachIndexed { _, res ->
                val circle = MapCircle(
                    MapPoint.mapPointWithGeoCoord(res.lat, res.lng),
                    1000,
                    argb(255, 255, 255, 0),
                    argb(128, 128, 128, 128)
                )

                circles.add(circle)
            }
        }

        return circles
    }

    fun randomArgbColor() : Int {
        val random = Random.Default
        val alpha = random.nextInt(256)
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)

        return Color.argb(alpha, red, green, blue)
    }
}
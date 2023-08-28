package com.demo.desh.util

import android.content.Context
import android.graphics.Color
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import kotlin.random.Random

object MapViewManager {
    fun onMapViewUpdate(mv: MapView, recommendInfo: RecommendInfo?) {
        mv.removeAllCircles()
        mv.removeAllPOIItems()

        var lats: Double = 0.0
        var lngs: Double = 0.0
        val size = recommendInfo?.list?.size ?: 1

        recommendInfo?.list?.forEach {
            lats += it.lat
            lngs += it.lng

            // Circle 추가
            val circle = MapCircle(
                MapPoint.mapPointWithGeoCoord(it.lat, it.lng),
                1500,
                randomArgbColor(),
                randomArgbColor()
            )

            mv.addCircle(circle)

            /*
            Log.e("MapViewManager", "${it.predict}, ${it.predict.formatDecimalSeparator()}")
            val markerItemName = "${it.district}\n${it.predict.formatDecimalSeparator()}"

            val marker = MapPOIItem()
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(it.lat, it.lng)
            marker.markerType = MapPOIItem.MarkerType.RedPin
            marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin
            marker.itemName = markerItemName

            mv.addPOIItem(marker)
            */

        }

        mv.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(lats / size, lngs / size),
            8,
            true
        )
    }

    private fun randomArgbColor(): Int {
        val random = Random.Default
        val a = random.nextInt(0, 256)
        val r = random.nextInt(0, 256)
        val g = random.nextInt(0, 256)
        val b = random.nextInt(0, 256)

        return Color.argb(a, r, g, b)
    }

    fun createMapView(recommendInfo: RecommendInfo?) : (context: Context) -> MapView {
        val markers = if (recommendInfo == null) getMapItems() else getMapItems(recommendInfo.list)
        return makeMapView(markers)
    }

    private fun makeMapView(markers: List<MapPOIItem>) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)
            markers.forEach { mv.addPOIItem(it) }
            mv
        }
    }

    /* recommendInfo -> Circle 생성 */
    private fun getMapItems(list: List<Recommend> = listOf()) : List<MapPOIItem> {
        val markers = mutableListOf<MapPOIItem>()

        if (list.isNotEmpty()) {
            list.forEach {
                val markerItemName = "${it.district}\n${it.predict.formatDecimalSeparator()}"

                val marker = MapPOIItem()
                marker.mapPoint = MapPoint.mapPointWithGeoCoord(it.lat, it.lng)
                marker.markerType = MapPOIItem.MarkerType.RedPin
                marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin
                marker.itemName = markerItemName

                markers.add(marker)
            }
        }

        return markers
    }
    private fun Long.formatDecimalSeparator(): String {
        return toString()
    }
}

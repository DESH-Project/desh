package com.demo.desh.util

import android.content.Context
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

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

            /* Circle 추가
            val circle = MapCircle(
                MapPoint.mapPointWithGeoCoord(it.lat, it.lng),
                1500,
                randomArgbColor(),
                randomArgbColor()
            )

            circle.tag = it.predict.toInt()

            mv.addCircle(circle)
            */

            val markerItemName = "(${it.service})\n${it.district} : ${it.predict}"

            val marker = MapPOIItem()
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(it.lat, it.lng)
            marker.markerType = MapPOIItem.MarkerType.RedPin
            marker.selectedMarkerType = MapPOIItem.MarkerType.BluePin
            marker.itemName = markerItemName

            mv.addPOIItem(marker)
        }

        mv.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(lats / size, lngs / size),
            8,
            true
        )
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
                val markerItemName = "(${it.service})\n${it.district} ${it.predict}"

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
}
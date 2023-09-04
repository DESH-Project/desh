package com.demo.desh.util

import android.content.Context
import android.util.Log
import com.demo.desh.MainActivity
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

object MapViewManager {
    fun onMapViewUpdate(mv: MapView, recommendInfo: RecommendInfo?, markerEventListener: MainActivity.MarkerEventListener) {
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
        }

        mv.fitMapViewAreaToShowAllPOIItems()
    }

    fun createMapView(recommendInfo: RecommendInfo?) : (context: Context) -> MapView {
        val markers = if (recommendInfo == null) getMapItems(null) else getMapItems(recommendInfo)
        return makeMapView(markers)
    }

    private fun makeMapView(markers: List<MapPOIItem>) : (context: Context) -> MapView {
        return { context: Context ->
            val mv = MapView(context)
            markers.forEach { mv.addPOIItem(it) }
            mv
        }
    }

    private fun getMapItems(recommendInfo: RecommendInfo?) : List<MapPOIItem> {
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
}

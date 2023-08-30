package com.demo.desh.util

import android.content.Context
import android.util.Log
import android.view.View
import com.demo.desh.MainActivity
import com.demo.desh.model.Recommend
import com.demo.desh.model.RecommendInfo
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

object MapViewManager {
    fun onMapViewUpdate(mv: MapView, recommendInfo: RecommendInfo?, markerEventListener: MainActivity.MarkerEventListener) {
        mv.removeAllCircles()
        mv.removeAllPOIItems()

        // mv.setCalloutBalloonAdapter(CustomBalloonAdapter())
        mv.setPOIItemEventListener(markerEventListener)

        recommendInfo?.list?.forEach {
            Log.e("MapViewManager", "${it.predict}, ${it.predict.formatDecimalSeparator()}")

            val marker = MapPOIItem()
                .apply {
                    itemName = it.district
                    mapPoint = MapPoint.mapPointWithGeoCoord(it.lat, it.lng)
                    markerType = MapPOIItem.MarkerType.RedPin
                    selectedMarkerType = MapPOIItem.MarkerType.BluePin
                }

            mv.addPOIItem(marker)
        }

        mv.fitMapViewAreaToShowAllPOIItems()
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

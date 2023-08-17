package com.demo.desh.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import net.daum.mf.map.api.MapView


@Composable
fun MapScreen(mapView: (context: Context) -> MapView, serviceList: List<String>) {
    val size = serviceList.size

    Column {
        CreateListButton(serviceList)

        AndroidView(
            factory = mapView,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CreateListButton(serviceList: List<String>) {
    LazyRow {
        itemsIndexed(serviceList) { idx, item ->
            Card(
                onClick = { },

                modifier = Modifier,

                elevation = 6.dp
            ) {
                Text(text = item)
            }
        }
    }
}
package com.demo.desh.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import net.daum.mf.map.api.MapView


@Composable
fun MapScreen(mapView: (context: Context) -> MapView, serviceList: List<String>) {
    Column {
        CreateListButton(serviceList)

        AndroidView(
            factory = mapView,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CreateListButton(serviceList: List<String>) {
    Row {
        serviceList.forEach { item -> TextButton(onClick = { }, content = { Text(text = item) }) }
    }
}
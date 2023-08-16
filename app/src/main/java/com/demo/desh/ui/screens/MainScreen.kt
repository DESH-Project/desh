package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.desh.model.User
import net.daum.mf.map.api.MapView

@Composable
fun MainScreen(user: User) {
    AndroidView(
        factory = { context ->
            val mapView = MapView(context)
            mapView
        },

        modifier = Modifier.fillMaxSize()
    )
}
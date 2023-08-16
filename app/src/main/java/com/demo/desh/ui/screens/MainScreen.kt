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
fun MainScreen(mapView: (context: Context) -> MapView) {
    val dumList = listOf("의류", "제빵", "문구", "의료", "카페", "치킨")

    Column {
        CreateListButton(dumList)

        AndroidView(
            factory = mapView,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CreateListButton(dums: List<String>) {
    Row {
        dums.forEach { dum -> TextButton(onClick = { }, content = { Text(text = dum) }) }
    }
}



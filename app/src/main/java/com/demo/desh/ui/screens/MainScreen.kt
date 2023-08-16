package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.demo.desh.model.User


@Composable
fun MainScreen(
    user: User,
    onMapButtonClick: () -> Unit,
) {
    Column {
        Button(onClick = onMapButtonClick) {
            Text("지도 보기")
        }
    }
}




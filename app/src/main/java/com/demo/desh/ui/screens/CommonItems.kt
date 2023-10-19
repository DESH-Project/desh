package com.demo.desh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = Modifier.then(modifier))
}

@Composable
fun CustomDivideLine() {
    val mod = Modifier
        .background(color = Color.White)
        .width(40.dp)
        .alpha(0.6f)

    Divider(modifier = mod, thickness = 4.dp)
}

@Composable
fun CustomFullDivideLine() {
    val mod = Modifier
        .background(color = Color.Gray)
        .fillMaxWidth()
        .alpha(0.2f)

    Divider(modifier = mod, thickness = 1.dp, startIndent = 8.dp)
}
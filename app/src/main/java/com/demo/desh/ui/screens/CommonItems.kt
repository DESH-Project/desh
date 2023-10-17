package com.demo.desh.ui.screens

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun CustomCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = Modifier.then(modifier))
}
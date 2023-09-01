package com.demo.desh.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun RealtyDetailScreen(
    realtyId: Long
) {
    Text(text = realtyId.toString())
}
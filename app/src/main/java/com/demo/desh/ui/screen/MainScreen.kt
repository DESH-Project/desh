package com.demo.desh.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.demo.desh.ui.theme.DeshprojectfeTheme

@Composable
fun MainActivityScreen() {
    Text(text = "MainScreen")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
        MainActivityScreen()
    }
}
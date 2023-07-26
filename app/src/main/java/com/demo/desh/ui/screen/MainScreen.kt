package com.demo.desh.ui.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.demo.desh.dto.User
import com.demo.desh.ui.theme.DeshprojectfeTheme

@Composable
fun MainActivityScreen(context: Context, user: User) {
    Text(text = "MainScreen")

    Column {
        Text(text = user.id!!.toString())
        Text(text = user.email)
        Text(text = user.nickname)
        
        Image(
            painter = rememberAsyncImagePainter(model = user.profileImageUrl),
            contentDescription = ""
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
    }
}
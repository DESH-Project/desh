package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.demo.desh.util.SocketManager

// ChatViewModel 연동 -> Socket 연동

@Composable
fun ChatRoomScreen(chatRoomId: Long) {
    SocketManager.init()

    var txt by rememberSaveable { mutableStateOf("") }

    Column {
        TextField(value = txt, onValueChange = { txt = it })
        Button(onClick = { SocketManager.send(txt) }) {
            Text(text = "Send")
        }
    }
}
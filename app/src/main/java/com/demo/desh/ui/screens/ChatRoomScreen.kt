package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.demo.desh.ui.CommonScaffoldForm
import com.demo.desh.ui.UserProfileCard
import com.demo.desh.util.SocketManager
import com.demo.desh.viewModel.ChatViewModel
import com.demo.desh.viewModel.UserViewModel
import java.time.LocalDate

// ChatViewModel 연동 -> Socket 연동

@Composable
fun ChatRoomScreen(
    chatRoomId: Long,
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel
) {
    LaunchedEffect(Unit) {
        SocketManager.init()
        chatViewModel.getChatDetail(chatRoomId)
    }
    
    /* STATES */
    val popen by chatViewModel.open.observeAsState(initial = false)
    val chatDetail by chatViewModel.chatDetail.observeAsState()
    var txt by rememberSaveable { mutableStateOf("") }
    val onTextChange = { text: String -> txt = text}
    
    CommonScaffoldForm(
        pbarOpen = popen, 
        topBarContent = { /*TODO*/ },
        bottomBarContent = { SendMsgForm(txt, onTextChange) }
    ) {
        chatDetail?.chats?.let {
            LazyColumn {
                items(it) {
                    ChatMsgBox(
                        userNickname = it.writer,
                        userImage = chatDetail!!.senderProfileUrl,
                        msg = it.message,
                        time = it.date.toLocalDate()
                    )
                }
            }
        }
    }
}

@Composable
fun ChatMsgBox(
    userNickname: String,
    userImage: String,
    msg: String,
    time: LocalDate
) {
    Column {

        UserProfileCard(
            userNickname = userNickname,
            userImage = userImage
        )

        Text(
            text = msg,
            fontSize = 16.sp,
            color = Color.White
        )

        Text(
            text = time.toString(),
            fontSize = 12.sp,
            color = Color.LightGray
        )
    }
}
@Composable
fun SendMsgForm(txt: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = txt,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.align(Alignment.CenterStart)
        )

        IconButton(
            onClick = { SocketManager.send(txt) },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = null)
        }
    }
}
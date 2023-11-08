package com.demo.desh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.model.ChatRoomPreviewInfo
import com.demo.desh.ui.CommonScaffoldForm

@Composable
fun ChatListScreen() {
    CommonScaffoldForm(
        scrollable = false,
        topBarContent = { /*TODO*/ }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (titleBarRef, chatRoomHolderRef, remainsMarginRef) = createRefs()

            ChatTitleBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(titleBarRef) {
                        top.linkTo(anchor = parent.top, margin = 48.dp)
                    }
            )

            ChatRoomHolder(
                chatRoomList = ChatRoomPreviewInfo.testData,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(chatRoomHolderRef) {
                        top.linkTo(anchor = titleBarRef.bottom, margin = 24.dp)
                        centerHorizontallyTo(parent)
                    }
            )

            // 마지막 여백
            Spacer(modifier = Modifier.constrainAs(remainsMarginRef) {
                top.linkTo(anchor = chatRoomHolderRef.bottom, margin = 60.dp)
            })
        }
    }
}

@Composable
fun ChatTitleBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Messages",
            fontSize = 48.sp,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(34.dp)
        )
    }
}

@Composable
fun ChatRoomHolder(
    chatRoomList: List<ChatRoomPreviewInfo>,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        LazyColumn {
            items(chatRoomList) {
                ChatRoomMaker(
                    senderName = it.senderName,
                    senderImage = it.senderImage,
                    chatPreview = it.chatPreview,
                    badgeCount = it.badgeCount,
                    onChatRoomClick = { }
                )

                val mod = Modifier
                    .background(color = Color.Gray)
                    .fillMaxWidth()
                    .alpha(0.2f)
                
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Divider(modifier = mod, thickness = Dp.Hairline, startIndent = 8.dp)
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
            }
        }
    }
}

@Composable
fun ChatRoomMaker(
    senderName: String,
    senderImage: String,
    chatPreview: String,
    badgeCount: Int,
    onChatRoomClick: () -> Unit
) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (profileCardRef, senderNameTextRef, chatPreviewTextRef, badgeCountRef) = createRefs()

            Card(
                modifier = Modifier
                    .size(48.dp)
                    .clip(shape = CircleShape)
                    .constrainAs(profileCardRef) {
                        start.linkTo(anchor = parent.start, margin = 12.dp)
                        centerVerticallyTo(parent)
                    }
            ) {
                AsyncImage(
                    model = senderImage,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = senderName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .constrainAs(senderNameTextRef) {
                        bottom.linkTo(anchor = chatPreviewTextRef.top)
                        start.linkTo(anchor = profileCardRef.end, margin = 12.dp)
                    }
            )

            Text(
                text = chatPreview,
                fontSize = 14.sp,
                color = Color.LightGray,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(chatPreviewTextRef) {
                        start.linkTo(anchor = profileCardRef.end, margin = 8.dp)
                        top.linkTo(anchor = senderNameTextRef.bottom)
                    }
            )

            if (badgeCount > 0) {
                TextButton(
                    enabled = false,
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        backgroundColor = Color.Red,
                        disabledContentColor = Color.White,
                    ),
                    modifier = Modifier
                        .size(20.dp)
                        .clip(shape = CircleShape)
                        .border(1.dp, Color.Red)
                        .wrapContentSize()
                        .constrainAs(badgeCountRef) {
                            end.linkTo(anchor = parent.end, margin = 12.dp)
                            centerVerticallyTo(parent)
                        }
                ) {
                    Text(
                        text = "$badgeCount",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                }
            }
    }
}
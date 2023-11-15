package com.demo.desh.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.ui.theme.DefaultBackgroundColor

@Composable
fun CommonScaffoldForm(
    pbarOpen: Boolean,
    topBarContent: @Composable () -> Unit,
    bottomBarContent: @Composable() (() -> Unit)? = { },
    mainContent: @Composable () -> Unit
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = topBarContent,
        bottomBar = bottomBarContent ?: { },
        backgroundColor = DefaultBackgroundColor,
        contentColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (pbarOpen) LoadingDialog(modifier = Modifier.align(Alignment.Center))
            else mainContent()
        }
    }
}

@Composable
fun TopBarContent(
    goToProfileScreen: () -> Unit,
    goToChatListScreen: () -> Unit,
    modifier: Modifier = Modifier,
    goToRealtyDetailScreen: () -> Unit = { }
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(DefaultBackgroundColor)
            .padding(5.dp)
    ) {
        Spacer(modifier = Modifier.padding(start = 5.dp))

        Text(
            text = "GOODPLACE",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            color = Color.White
        )

        Row(
            modifier = Modifier.padding(start = 30.dp, end = 5.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CustomIconMenu(
                vector = Icons.Default.Email,
                onIconClick = goToChatListScreen
            )

            CustomIconMenu(
                vector = Icons.Default.AccountCircle,
                onIconClick = goToProfileScreen
            )

            CustomIconMenu(
                vector = Icons.Default.Warning,
                onIconClick = goToRealtyDetailScreen
            )
        }
    }
}

@Composable
fun CustomIconMenu(
    vector: ImageVector,
    onIconClick: () -> Unit,
    tint: Color = Color.White
) {
    IconButton(onClick = onIconClick) {
        Icon(
            imageVector = vector,
            contentDescription = null,
            tint = tint,
            modifier = Modifier
                .size(25.dp)
                .background(color = DefaultBackgroundColor, shape = CircleShape)
        )
    }
}

@Composable
fun UserProfile(
    userNickname: String,
    profileImageUrl: String,
    userDescription: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(CircleShape)
                .size(120.dp)
        )

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userNickname,
                fontSize = 24.sp,
                color = Color.White
            )

            Text(
                text = userDescription ?: "유저 소개가 없습니다.",
                fontSize = 13.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 62.dp)
            )
        }
    }
}

@Composable
fun UserProfileCard(
    userNickname: String,
    userImage: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.size(48.dp)
        ) {
            AsyncImage(
                model = userImage,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.padding(start = 10.dp))

        Text(
            text = userNickname,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.LightGray
        )
    }
}

@Composable
fun LoadingDialog(
    modifier: Modifier = Modifier,
    progressIndicatorSize: Dp = 80.dp,
    progressIndicatorColor: Color = Color.White
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = keyframes { durationMillis = 600 }),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .size(progressIndicatorSize)
                .rotate(angle)
                .border(
                    width = 8.dp,
                    brush = Brush.sweepGradient(
                        listOf(
                            Color.White, // add background color first
                            progressIndicatorColor.copy(alpha = 0.1f),
                            progressIndicatorColor
                        )
                    ),
                    shape = CircleShape
                ),
            strokeWidth = 1.dp,
            color = Color.White // Set background color
        )

        Text(
            text = "데이터를 불러오고 있어요..",
            color = Color.LightGray,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}
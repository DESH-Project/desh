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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.demo.desh.model.DropdownItem
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
    modifier: Modifier = Modifier
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    val onDropdownExpand = { isDropDownExpanded = true }
    val onDropdownClose = { isDropDownExpanded = false }

    val dropdownItems = listOf(
        DropdownItem("공유하기", Color.Black),
        DropdownItem("신고하기", Color.Red)
    )

    Row(
        modifier = modifier
            .padding(top = 4.dp)
            .background(DefaultBackgroundColor),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "GOODPLACE",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = Color.White
        )

        Row {
            CustomIconMenu(
                vector = Icons.Default.Email,
                onIconClick = goToChatListScreen
            )

            CustomIconMenu(
                vector = Icons.Default.AccountCircle,
                onIconClick = goToProfileScreen
            )

            CustomDropdownMenu(
                isDropDownExpanded = isDropDownExpanded,
                onDropdownExpand = onDropdownExpand,
                onDropdownClose = onDropdownClose,
                items = dropdownItems,
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
                .size(35.dp)
                .background(color = Color(0x20C7C1C1), shape = CircleShape)
        )
    }
}

@Composable
fun CustomDropdownMenu(
    isDropDownExpanded: Boolean,
    onDropdownExpand: () -> Unit,
    onDropdownClose: () -> Unit,
    items: List<DropdownItem>,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        IconButton(onClick = onDropdownExpand) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Color.White,
            )
        }

        DropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = onDropdownClose,
            modifier = Modifier.wrapContentSize()
        ) {
            items.forEachIndexed { idx, item ->
                DropdownMenuItem(onClick = { }) {
                    Text(
                        text = item.text,
                        color = item.textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                if (items.size - 1 > idx) {
                    Divider(color = Color.White)
                }
            }
        }
    }
}

@Composable
fun UserProfileCard(
    userNickname: String,
    userImage: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(48.dp)
                .clip(shape = RoundedCornerShape(50.dp))
                .align(Alignment.CenterStart)
        ) {
            AsyncImage(
                model = userImage,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.padding(start = 8.dp))

        Text(
            text = userNickname,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.TopCenter)
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
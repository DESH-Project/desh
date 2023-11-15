package com.demo.desh.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    mainContent: @Composable () -> Unit
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = topBarContent,
        backgroundColor = DefaultBackgroundColor,
        contentColor = Color.White,
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (pbarOpen) LoadingDialog()
            else mainContent()
        }
    }
}

@Composable
fun CustomFullDivideLine() {
    val mod = Modifier
        .background(color = Color.Gray)
        .fillMaxWidth()
        .alpha(0.2f)

    Divider(modifier = mod, thickness = 1.dp, startIndent = 8.dp)
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
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(48.dp)
                .clip(shape = CircleShape)
        ) {
            AsyncImage(
                model = userImage,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.padding(start = 8.dp))

        Column {
            Text(text = userNickname, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = userNickname, fontSize = 12.sp)
        }
    }
}

@Composable
fun LoadingDialog(
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DefaultBackgroundColor)
    ) {
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .align(Alignment.Center)
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
    }
}
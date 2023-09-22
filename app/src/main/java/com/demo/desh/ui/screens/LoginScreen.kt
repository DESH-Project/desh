package com.demo.desh.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.R
import com.demo.desh.login.KakaoLogin
import com.demo.desh.model.LoginPreviewInfo
import com.demo.desh.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    goToMapScreen: () -> Unit
) {
    val context = LocalContext.current
    val textData = LoginPreviewInfo.textData

    val previewImages by viewModel.previewImages.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPreviewImages()
    }

    Scaffold(
        floatingActionButton = {
            SocialLoginButton(
                context = context,
                viewModel = viewModel,
                goToMapScreen = goToMapScreen
            )
        },

        floatingActionButtonPosition = FabPosition.Center

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {

            HorizontalPager(
                count = previewImages?.size ?: 1,
                modifier = Modifier
                    .fillMaxSize()
            ) { pageIndex ->

                val size = previewImages?.size ?: 1
                val model = previewImages?.get(pageIndex) ?: ""

                AsyncImage(
                    model = model,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxHeight()
                )

                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(12.dp)
                        .size(36.dp)
                        .alpha(if (pageIndex == size - 1) 0f else 0.45f)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(32.dp)
                ) {
                    Text(
                        text = textData[pageIndex].introduceText,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = textData[pageIndex].impactText,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = textData[pageIndex].explainText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialLoginButton(
    context: Context,
    viewModel: MainViewModel,
    goToMapScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val onKakaoLoginButtonClick = { coroutineScope.launch { KakaoLogin.login(context, viewModel, goToMapScreen) } }

    IconButton(
        onClick = { onKakaoLoginButtonClick() },
        modifier = Modifier
            .width(256.dp)
            .height(90.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login_large_wide),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        )
    }
}
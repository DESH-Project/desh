package com.demo.desh.ui.screens

import android.content.Context
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.R
import com.demo.desh.login.KakaoLogin
import com.demo.desh.login.NaverLogin
import com.demo.desh.model.LoginPreviewInfo
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(
    goToMapScreen: () -> Unit
) {
    val context = LocalContext.current
    val testData = LoginPreviewInfo.testData

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            Column {
                HorizontalPager(
                    count = testData.size,
                    modifier = Modifier
                        .fillMaxSize()
                ) { pageIndex ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = testData[pageIndex].imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
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
                                .alpha(if (pageIndex == testData.size - 1) 0.1f else 0.45f)
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(24.dp)
                        ) {
                            Text(
                                text = testData[pageIndex].introduceText,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Text(
                                text = testData[pageIndex].impactText,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )

                            Text(
                                text = testData[pageIndex].explainText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {

                Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 120.dp))

                SocialLoginButtons(context = context, goToMapScreen)
            }
        }
    }
}


@Composable
private fun SocialLoginButtons(context: Context, goToMapScreen: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.padding(4.dp))

        SocialLoginIconButton(
            imageResource = R.drawable.kakao_login_large_wide,
            onClick = { coroutineScope.launch { KakaoLogin.login(context, goToMapScreen) } }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        SocialLoginIconButton(
            imageResource = R.drawable.naver_login,
            onClick = { coroutineScope.launch { NaverLogin.login(context, goToMapScreen) } }
        )
    }
}

@Composable
private fun SocialLoginIconButton(
    imageResource: Int,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
        )
    }
}
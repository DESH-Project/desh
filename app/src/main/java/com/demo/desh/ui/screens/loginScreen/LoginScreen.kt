package com.demo.desh.ui.screens.loginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.model.LoginPreviewInfo
import com.demo.desh.model.User
import com.demo.desh.ui.theme.Typography2
import com.demo.desh.util.KakaoLogin
import com.demo.desh.viewModel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    goToStartScreen: () -> Unit,
) {
    LaunchedEffect(Unit) {
        userViewModel.loadPreviewImages()
    }

    val context = LocalContext.current
    val textData = LoginPreviewInfo.textData
    val pagerState = rememberPagerState()

    /* STATES */
    val previewImages by userViewModel.previewImages.observeAsState()

    /* HANDLERS */
    val onKakaoLoginButtonClick = {
        val goToStartScreenWithUser = { user: User? ->
            goToStartScreen()
            userViewModel.fetchUser(user)
        }

        KakaoLogin.login(context, goToStartScreenWithUser)
    }

    Scaffold(
        floatingActionButton = { SocialLoginButton(onKakaoLoginButtonClick) },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            val size = previewImages?.size ?: 1

            HorizontalPager(
                count = size,
                state = pagerState
            ) { pageIndex ->
                val model = previewImages?.get(pageIndex) ?: ""

                AsyncImage(
                    model = model,
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier.padding(bottom = 290.dp)
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

                Column(modifier = Modifier.padding(top = 410.dp)) {
                    Text(
                        text = textData[pageIndex].introduceText,
                        style = Typography2.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 29.dp)
                    )

                    Text(
                        text = textData[pageIndex].impactText,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        modifier = Modifier.padding(horizontal = 27.dp)
                    )

                    Text(
                        text = textData[pageIndex].explainText,
                        style = Typography2.bodySmall,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 29.dp)
                    )
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(top = 462.dp, start = 32.dp),
                indicatorHeight = 5.dp,
                spacing = 5.dp,
                activeColor = Color.White,
                indicatorShape = RectangleShape,
                indicatorWidth = 32.dp
            )
        }
    }
}
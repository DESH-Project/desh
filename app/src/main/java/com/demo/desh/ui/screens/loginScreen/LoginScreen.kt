package com.demo.desh.ui.screens.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.R
import com.demo.desh.model.LoginPreviewInfo
import com.demo.desh.model.User
import com.demo.desh.ui.theme.HighlightColor
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

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.DarkGray)
        ) {
            val size = previewImages?.size ?: 1

            if (previewImages == null) {
                CircularProgressIndicator()
            }

            HorizontalPager(
                count = size,
                state = pagerState
            ) { pageIndex ->
                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (imageRef, indicatorRef, textRef, loginBtnRef) = createRefs()
                    val model = previewImages?.get(pageIndex) ?: ""
                    val isLast = pageIndex == size - 1

                    AsyncImage(
                        model = model,
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (isLast) 540.dp else 550.dp)
                            .constrainAs(imageRef) {
                                centerHorizontallyTo(parent)
                            }
                    )

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        indicatorHeight = 5.dp,
                        spacing = 5.dp,
                        activeColor = Color.White,
                        indicatorShape = RectangleShape,
                        indicatorWidth = 32.dp,
                        modifier = Modifier
                            .constrainAs(indicatorRef) {
                                top.linkTo(anchor = imageRef.bottom)
                                centerHorizontallyTo(imageRef)
                                bottom.linkTo(anchor = textRef.top)
                            }
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.constrainAs(textRef) {
                            top.linkTo(anchor = indicatorRef.bottom, margin = 18.dp)
                            start.linkTo(anchor = parent.start, margin = 20.dp)
                        }
                    ) {
                        Text(
                            text = textData[pageIndex].introduceText,
                            style = Typography2.bodyMedium,
                            color = HighlightColor,
                        )

                        Spacer(
                            modifier = Modifier
                                .padding(vertical = if (isLast) 0.dp else 20.dp)
                        )

                        Text(
                            text = textData[pageIndex].impactText,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 45.sp,
                        )

                        Text(
                            text = textData[pageIndex].explainText,
                            style = Typography2.bodySmall,
                            color = Color.White,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }

                    if (isLast) {
                        SocialLoginButton(
                            onKakaoLoginButtonClick = onKakaoLoginButtonClick,
                            modifier = Modifier
                                .constrainAs(loginBtnRef) {
                                    top.linkTo(anchor = textRef.bottom)
                                    bottom.linkTo(anchor = parent.bottom, margin = 12.dp)
                                    start.linkTo(anchor = parent.start, margin = 12.dp)
                                    end.linkTo(anchor = parent.end, margin = 12.dp)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SocialLoginButton(
    onKakaoLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onKakaoLoginButtonClick() }
            .background(Color.DarkGray)
            .clip(shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login_large_wide),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}
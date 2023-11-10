package com.demo.desh.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.demo.desh.ui.CommonScaffoldForm
import com.demo.desh.ui.LoadingDialog
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
    goToMapScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        userViewModel.loadPreviewImages()
    }

    val context = LocalContext.current
    val textData = LoginPreviewInfo.textData
    val pagerState = rememberPagerState()

    /* STATES */
    val open by userViewModel.open.observeAsState(initial = true)
    val previewImages by userViewModel.previewImages.observeAsState()

    /* HANDLERS */
    val onKakaoLoginButtonClick = {
        val goToMapScreenWithUser = { user: User ->
            userViewModel.fetchUser(user)
            goToMapScreen()
        }

        KakaoLogin.login(context, goToMapScreenWithUser)
    }

    CommonScaffoldForm(
        pbarOpen = open,
        topBarContent = { /*TODO*/ }
    ) {
        val size = previewImages?.size ?: 1

        HorizontalPager(
            count = size,
            state = pagerState
        ) { pageIndex ->
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (imageRef, indicatorRef, userManualPageRef) = createRefs()
                val model = previewImages?.get(pageIndex) ?: ""

                AsyncImage(
                    model = model,
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(530.dp)
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
                            bottom.linkTo(anchor = userManualPageRef.top)
                        }
                )

                when (pageIndex) {
                    (size - 1) -> {
                        LastPage(
                            onKakaoLoginButtonClick = onKakaoLoginButtonClick,
                            modifier = Modifier
                                .constrainAs(userManualPageRef) {
                                    top.linkTo(anchor = indicatorRef.bottom, margin = 16.dp)
                                    centerHorizontallyTo(other = parent)
                                }
                        )
                    }

                    in (0 until size - 1) -> {
                        UserManualPage(
                            textData = textData[pageIndex],
                            modifier = Modifier.constrainAs(userManualPageRef) {
                                top.linkTo(anchor = indicatorRef.bottom, margin = 16.dp)
                                start.linkTo(anchor = parent.start, margin = 20.dp)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserManualPage(
    textData: LoginPreviewInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = textData.introduceText,
            style = Typography2.bodyMedium,
            color = HighlightColor
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = textData.impactText,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
        )

        Text(
            text = textData.explainText,
            style = Typography2.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}

@Composable
fun LastPage(
    onKakaoLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome!",
            color = HighlightColor,
            fontWeight = FontWeight.Bold,
            fontSize = 52.sp,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "소상공인을 위한 상권추천 서비스\nGoodPlace를 지금 바로 시작하세요!",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(start = 2.dp)
        )

        Spacer(modifier = Modifier.padding(15.dp))

        Card(
            modifier = Modifier
                .clickable { onKakaoLoginButtonClick() }
                .background(Color.DarkGray)
                .clip(shape = CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao_login_large_wide),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(280.dp)
                    .height(42.dp)
            )
        }
    }
}

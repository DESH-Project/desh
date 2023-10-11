package com.demo.desh.ui.screens.loginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.demo.desh.model.LoginPreviewInfo
import com.demo.desh.ui.screens.common.CommonItems
import com.demo.desh.ui.screens.loginScreen.items.LoginScreenItems
import com.demo.desh.viewModel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    goToStartScreen: () -> Unit,
) {
    val context = LocalContext.current
    val textData = LoginPreviewInfo.textData

    /* STATES */
    val userLoading by userViewModel.userLoading.observeAsState()
    val previewImages by userViewModel.previewImages.observeAsState()

    /* HANDLERS */
    val onKakaoLoginButtonClick = {
        userViewModel.kakaoLogin(context)
        goToStartScreen()
    }

    LaunchedEffect(Unit) {
        userViewModel.loadPreviewImages()
    }

    Scaffold(
        floatingActionButton = {
            LoginScreenItems.SocialLoginButton(
                isLoading = userLoading ?: false,
                onKakaoLoginButtonClick = onKakaoLoginButtonClick
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

            /* 이미지 로딩 */
            if (previewImages == null) {
                CommonItems.CustomCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            else {
                HorizontalPager(
                    count = previewImages?.size ?: 1,
                    modifier = Modifier
                        .fillMaxSize()
                ) { pageIndex ->

                    val size = previewImages?.size ?: 1
                    val model = previewImages?.get(pageIndex) ?: ""

                    LoginScreenItems.CustomAsyncImage(
                        model = model,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    LoginScreenItems.CustomArrowNextIcon(
                        pageIndex = pageIndex,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        size = size
                    )

                    LoginScreenItems.LoginIntroTextColumn(
                        textData = textData,
                        modifier = Modifier.align(Alignment.TopStart),
                        pageIndex = pageIndex
                    )
                }
            }
        }
    }
}


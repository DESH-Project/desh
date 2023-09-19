package com.demo.desh.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.desh.R
import com.demo.desh.login.KakaoLogin
import com.demo.desh.login.NaverLogin
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    goToMapScreen: () -> Unit
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LogoText()

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 120.dp))

        SocialLoginButtons(context = context, goToMapScreen)
    }
}

@Composable
private fun LogoText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DESH",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 80.sp,
            fontStyle = FontStyle.Italic,
            color = Color.LightGray
        )

        Text(
            text = "소상공인을 위한 상권 추천 서비스",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal,
            color = Color.DarkGray
        )
    }
}

@Composable
private fun SocialLoginButtons(context: Context, goToMapScreen: () -> Unit) {
    val loginWithKakaoText = "Sign In With Kakao"
    val loginWithNaverText = "Sign In With Naver"
    val loginGuideText = "아래 계정으로 서비스 시작하기"

    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(256.dp)
    ) {
        Text(
            text = loginGuideText,
            color = Color.Black.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.padding(4.dp))

        SocialLoginIconButton(
            text = loginWithKakaoText,
            imageResource = R.drawable.kakao_login_large_narrow,
            onClick = { coroutineScope.launch { KakaoLogin.login(context, goToMapScreen) } }
        )

        SocialLoginIconButton(
            text = loginWithNaverText,
            imageResource = R.drawable.naver_login,
            onClick = { coroutineScope.launch { NaverLogin.login(context, goToMapScreen) } }
        )
    }
}

@Composable
private fun SocialLoginIconButton(
    text: String,
    imageResource: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = text,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(256.dp)
                .height(80.dp)
        )
    }
}
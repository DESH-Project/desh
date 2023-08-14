package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.KakaoLogin
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DeshprojectfeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LoginActivityScreen()
                }
            }
        }
    }
}

@Composable
fun LoginActivityScreen() {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogoText()

        Divider(modifier = Modifier.padding(10.dp))

        SocialLoginButtons(context = context)
    }
}

@Composable
private fun LogoText() {
    Text(
        text = "DESH",
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
        fontStyle = FontStyle.Italic,
        color = Color.DarkGray
    )

    Spacer(modifier = Modifier.padding(2.dp))

    Text(
        text = "소상공인을 위한 상권 추천 서비스",
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontStyle = FontStyle.Normal,
        color = Color.DarkGray
    )
}

@Composable
private fun SocialLoginButtons(context: Context) {
    val loginWithKakaoText = "Sign In With Kakao"
    val loginGuideText = "아래 계정으로 서비스 시작하기"

    val coroutineScope = rememberCoroutineScope()

    Text(
        text = loginGuideText,
        color = Color.Black.copy(alpha = 0.7f),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )

    Spacer(modifier = Modifier.padding(4.dp))

    SocialLoginIconButton(
        text = loginWithKakaoText,
        imageResource = R.drawable.kakao_login_large_narrow,
        onClick = { coroutineScope.launch { KakaoLogin.login(context) } }
    )

    Spacer(modifier = Modifier.padding(4.dp))
}

@Composable
private fun SocialLoginIconButton(
    text: String,
    imageResource: Int,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .width(240.dp)
            .height(48.dp)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = text,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginActivityScreenPreview() {
    DeshprojectfeTheme {
        LoginActivityScreen()
    }
}
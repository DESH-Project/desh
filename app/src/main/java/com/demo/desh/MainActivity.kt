package com.demo.desh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.demo.desh.dto.KakaoUser
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.util.KakaoLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KakaoLogin.init(this)

        setContent {
            DeshprojectfeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainActivityScreen()
                }
            }
        }
    }

    companion object {
        const val LOGIN_TAG = "MainActivity_LOGIN"
    }
}

@Composable
fun MainActivityScreen() {
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
fun LogoText() {
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
fun SocialLoginButtons(context: Context) {
    val loginWithKakaoText = "Sign In With Kakao"
    val loginWithGoogleText = "Sign In With Google"
    val loginGuideText = "아래 계정으로 서비스 시작하기"

    Text(
        text = loginGuideText,
        color = Color.Black.copy(alpha = 0.7f),
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )

    Spacer(modifier = Modifier.padding(4.dp))

    SocialLoginButton(
        text = loginWithKakaoText,
        imageResource = R.drawable.kakao_login_large_narrow,
        onClick = { KakaoLogin.kakaoLogin(context) }
    )

    Spacer(modifier = Modifier.padding(4.dp))

    SocialLoginButton(
        text = loginWithGoogleText,
        imageResource = R.drawable.btn_google_signin_light_focus_web,
    )

    Spacer(modifier = Modifier.padding(4.dp))

    TestLoginButtonWithMockUser(context = context)
}

@Composable
fun SocialLoginButton(
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

@Composable
private fun TestLoginButtonWithMockUser(context: Context) {
    val onButtonClick = {
        val mockUser = KakaoUser(
            id = -1L,
            nickname = "황승수",
            ageRange = "AGE_20_29",
            profileImageUrl = "https://k.kakaocdn.net/dn/JEY2d/btsmuprjeuP/BZOMvMtSrWze5Ymq2hoJX1/img_640x640.jpg",
            email = "h970126@gmail.com",
            gender = "MAIL"
        )

        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra("user", mockUser)
        context.startActivity(intent)
    }

    Button(onClick = onButtonClick) {
        Text(text = "Test Button To Next Step")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
        MainActivityScreen()
    }
}

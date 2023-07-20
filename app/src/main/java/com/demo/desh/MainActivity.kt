package com.demo.desh

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.desh.api.RetrofitClient
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

private const val TAG = "MainActivity"
private var loginSuccess = false
private lateinit var code: String

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nativeAppKey = applicationContext.resources.getString(R.string.KAKAO_NATIVE_APP_KEY)
        KakaoSdk.init(this, nativeAppKey)

        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")
        Log.d(TAG, "kakao_native_app_key : $nativeAppKey")

        setContent {
            DeshprojectfeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityScreen()
                }
            }
        }
    }
}

@Composable
fun MainActivityScreen() {
    val toolbarText = "로그인"
    val loginWithKakaoText = "Login With Kakao"
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //ToolbarWithMenu(name = toolbarText)

        SocialLoginButton(
            text = loginWithKakaoText,
            imageResource = R.drawable.kakao_login_large_narrow,
            onClick = { kakaoLogin(context) }
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    imageResource: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = text
        )
    }
}

private fun kakaoLogin(context: Context) {
    val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) Log.e(TAG, "로그인 실패 $error")
        else if (token != null) Log.e(TAG, "로그인 성공 ${token.accessToken}")
    }

    val instance = UserApiClient.instance

    Log.d("kakaoLogin()", "Kakao Login 시도")

    if (instance.isKakaoTalkLoginAvailable(context)) {
        instance.loginWithKakaoTalk(context) { token, error ->
            // 로그인 실패
            if (error != null) {
                Log.e(TAG, "로그인 실패 $error")

                // 사용자가 취소 버튼을 눌렀을 때
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }

                else {
                    instance.loginWithKakaoAccount(context, callback = mCallback)
                }
            }

            // 로그인 성공
            else if (token != null) {
                code = token.accessToken
                loginSuccess = true
                Log.e(TAG, "로그인 성공 ${code}}")

                // TODO: 백엔드로 승인코드 전달하는 로직 작성
            }
        }
    }

    instance.me { user, error ->
        if (error != null) Log.e(TAG, "사용자 정보 요청 실패 $error")
        else if (user != null) {
            Log.e(TAG, "사용자 정보 요청 성공 $user")

            val account = user.kakaoAccount

            val nickname = account?.profile?.nickname
            val ageRange = account?.ageRange?.toString()
            val email = account?.email
            val profileImageUrl = account?.profile?.profileImageUrl
            val gender = account?.gender?.toString()

            println("$nickname $ageRange $email $profileImageUrl $gender")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
        MainActivityScreen()
    }
}
package com.demo.desh

import android.content.Context
import android.content.Intent
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
import com.demo.desh.api.ApiService
import com.demo.desh.api.RetrofitClient
import com.demo.desh.dto.KakaoUser
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

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
        
        Spacer(modifier = Modifier.padding(120.dp))

        TestLoginButton(context = context)
    }
}

@Composable
private fun TestLoginButton(context: Context) {
    val onButtonClick = {
        val mockUser = KakaoUser(
            id = -1L,
            nickname = "황승수",
            ageRange = "AGE_20_29",
            profileImageUrl = "https://k.kakaocdn.net/dn/JEY2d/btsmuprjeuP/BZOMvMtSrWze5Ymq2hoJX1/img_640x640.jpg",
            email = "h970126@gmail.com",
            gender = "MAIL",
            refreshToken = "test-refreshToken",
            accessToken = "test-accessToken"
        )

        val intent = Intent(context, SurveyActivity::class.java)
        intent.putExtra("user", mockUser)
        context.startActivity(intent)
    }

    Button(onClick = onButtonClick) {
        Text(text = "Test Button To Next Step")
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

    var isSuccess = false
    var getUserInfoSuccess = false
    val instance = UserApiClient.instance
    var code: String? = null
    var kakaoUser: KakaoUser? = null

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
                isSuccess = true
                Log.e(TAG, "로그인 성공 ${code}}")
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

            kakaoUser = KakaoUser(
                nickname = nickname!!,
                ageRange = ageRange!!,
                email = email!!,
                profileImageUrl = profileImageUrl!!,
                gender = gender!!
            )

            getUserInfoSuccess = true
            println("$nickname $ageRange $email $profileImageUrl $gender")
        }
    }

    if (isSuccess && getUserInfoSuccess && code != null && kakaoUser != null) {
        sendToServer(context, code!!, kakaoUser!!)
    }
}

private fun sendToServer(context: Context, code: String, kakaoUser: KakaoUser) {
    val retrofit = RetrofitClient.getClient(RetrofitClient.domain)
    val apiService = retrofit.create(ApiService::class.java)
    val result = apiService.sendAuthCode(code)

    result.enqueue(object : Callback<Long> {
        override fun onResponse(call: Call<Long>, response: Response<Long>) {
            val accessToken = response.headers()["Authorization"]
            val refreshToken = response.headers()["Authorization-Refresh"]
            val id = response.body()

            kakaoUser.accessToken = accessToken
            kakaoUser.refreshToken = refreshToken
            kakaoUser.id = id

            val intent = Intent(context, SurveyActivity::class.java)
            intent.putExtra("user", kakaoUser)
            context.startActivity(intent)
        }

        override fun onFailure(call: Call<Long>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}

@Preview(showBackground = true)
@Composable
fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
        MainActivityScreen()
    }
}
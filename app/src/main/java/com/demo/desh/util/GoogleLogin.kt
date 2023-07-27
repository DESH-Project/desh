package com.demo.desh.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.demo.desh.MainActivity
import com.demo.desh.R
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GoogleLogin: SocialLogin {
    @SuppressLint("StaticFieldLeak")
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private const val TAG = "GoogleLLoginObject"

    override fun init(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(context.getString(R.string.GOOGLE_CLIENT_ID))
            .requestId()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun getSignInClient(): GoogleSignInClient {
        return mGoogleSignInClient
    }

    fun alreadyLoginCheck(context: Context): GoogleSignInAccount? {
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    fun handleLoginTask(context: Context, task: Task<GoogleSignInAccount>) {
        val account = task.result
        Log.d(TAG, "구글 로그인 성공")
        Log.d(TAG, account.toString())

        val user = User(
            email = account.email!!,
            nickname = account.account!!.name!!,
            profileImageUrl = account.photoUrl.toString()
        )

        send(context, user)
    }

    override fun send(context: Context, user: User) {
        val userService = RetrofitClient.userService
        val login = userService.login(user)

        login.enqueue(object : Callback<Long> {
            override fun onResponse(call: Call<Long>, response: Response<Long>) {
                user.id = response.body()
                intentNext(context, user)
            }

            override fun onFailure(call: Call<Long>, t: Throwable) {
                val loginFailText = "Google Login에 실패하였습니다"

                Log.e(TAG, loginFailText)
                Log.e(TAG, t.message!!)
                Toast.makeText(context, loginFailText, Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun intentNext(context: Context, user: User) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("user", user)
        context.startActivity(intent)
    }
}

package com.example.desh.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.desh.NavRoutes
import com.example.desh.api.RetrofitAPI
import com.example.desh.domain.User
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun Home(navController: NavController) {
    val ctx = LocalContext.current
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }

    val onUserEmailChange = { text: String -> userEmail = text }
    val onUserPasswordChange = { text: String -> userPassword = text }
    val onResponseChange = { text: String -> response = text }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomTextField(
                title = "Enter your Email",
                textState = userEmail,
                onTextChange = onUserEmailChange
            )

            CustomTextField(
                title = "Enter your Password",
                textState = userPassword,
                onTextChange = onUserPasswordChange
            )

            Spacer(modifier = Modifier.size(30.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO) {
                            postData(
                                ctx = ctx,
                                userEmail = mutableStateOf(userEmail),
                                userPassword = mutableStateOf(userPassword),
                                result = mutableStateOf(response),
                                onResponseChange = onResponseChange
                            )
                        }

                        if (response != "unknown") {
                            navController.navigate(NavRoutes.Welcome.route)
                        }

                        else {
                            Toast.makeText(ctx, "아이디 또는 비밀번호가 틀립니다 : $response", Toast.LENGTH_SHORT).show()
                            navController.navigate(NavRoutes.Home.route)
                        }
                    }
                }
            ) {
                Text(text = "Sign In")
            }
        }
    }
}

@Composable
fun CustomTextField(title: String, textState: String, onTextChange: (String) -> Unit) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onTextChange(it) },
        singleLine = true,
        label = { Text(title) },
        modifier = Modifier.padding(10.dp),
        textStyle = TextStyle(fontSize = 20.sp)
    )
}

private fun postData(
    ctx: Context,
    userEmail: MutableState<String>,
    userPassword: MutableState<String>,
    result: MutableState<String>,
    onResponseChange: (String) -> Unit
) {

    val url = "https://six-knives-grow-222-120-178-113.loca.lt"

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val loginUser = User(
        email = userEmail.value,
        password = userPassword.value
    )

    val call: Call<User>? = retrofitAPI.loginUser(loginUser)

    call?.enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            val model = response.body()
            val findUser = User(
                email = model?.email ?: "unknown email",
                password = model?.password ?: "unknown password"
            )

            val statusCode = response.code()

            if (statusCode == 200 && loginUser == findUser) {
                Toast.makeText(ctx, "${findUser.email}", Toast.LENGTH_SHORT).show()
                onResponseChange(findUser.email!!)
            } else {
                onResponseChange("unknown")
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Toast.makeText(ctx, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    })
}
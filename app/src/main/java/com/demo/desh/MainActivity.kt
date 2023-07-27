package com.demo.desh

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.demo.desh.model.User
import com.demo.desh.ui.theme.DeshprojectfeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getSerializableExtra("user") as User

        setContent {
            DeshprojectfeTheme {
                Surface {
                    MainActivityScreen(this, user)
                }
            }
        }
    }
}

@Composable
fun MainActivityScreen(context: Context, user: User) {
    Text(text = "MainScreen")

    Column {
        Text(text = user.id!!.toString())
        Text(text = user.email)
        Text(text = user.nickname)

        Image(
            painter = rememberAsyncImagePainter(model = user.profileImageUrl),
            contentDescription = ""
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun MainActivityScreenPreview() {
    DeshprojectfeTheme {
    }
}
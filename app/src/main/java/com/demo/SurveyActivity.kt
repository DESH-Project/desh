package com.demo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.desh.ui.theme.DeshprojectfeTheme

class SurveyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeshprojectfeTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SurveyScreen()
                }
            }
        }
    }
}

@Composable
fun SurveyScreen() {
    val appBarText = "서비스 조사"
    val nextButtonText = "Next"

    ToolbarWithMenu(name = appBarText)

    Spacer(modifier = Modifier.padding(horizontal = 120.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(35.dp))

        SurveyText()

        Survey()

        Spacer(modifier = Modifier.padding(12.dp))

        Button(
            onClick = {},
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .height(44.dp)
                .width(99.dp)
        ) {
            Text(text = nextButtonText, fontSize = 15.sp, color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithMenu(name: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "MoreVert",
                        )
                    }
                }
            )
        }
    ) {
    }
}

@Composable
private fun SurveyText() {
    val logoText = "Good Place"
    val firstGuideText = "상권 추천을 위해 설문에 답변해주세요!"
    val secondGuideText = "타겟층 연령을 선택하세요!"

    Text(text = logoText, fontSize = 23.sp, color = Color.Blue)

    Spacer(modifier = Modifier.padding(18.dp))

    Text(
        text = firstGuideText,
        fontSize = 15.sp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.padding(8.dp))

    Text(
        text = secondGuideText,
        fontSize = 17.sp,
        color = Color.Black
    )
}

@Composable
private fun Survey() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val bgColor = if (isPressed) Color.White else Color.White
    val context = LocalContext.current

    for (i in 1..4) {
        val ageText = "${i}0대"
        val age = i * 10

        Spacer(modifier = Modifier.padding(12.dp))

        TargetButton(
            context = context,
            interactionSource = interactionSource,
            bgColor = bgColor,
            text = ageText,
            age = age
        )
    }
}

@Composable
private fun TargetButton(
    context: Context,
    interactionSource: MutableInteractionSource,
    bgColor: Color,
    text: String,
    age: Int
) {
    val onClickText = "${age}대를 선택하셨네요!"

    OutlinedButton(
        onClick = { showToast(context, onClickText) },
        border = BorderStroke(1.dp, Color.Blue),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(44.dp)
            .clickable(
                onClick = {}, interactionSource = interactionSource,
                indication = rememberRipple(bounded = false)
            )

    ) {
        Text(text = text, fontSize = 15.sp, color = Color.Black)
    }
}

private fun showToast(context: Context, message: String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeshprojectfeTheme(){
        SurveyScreen()
    }
}
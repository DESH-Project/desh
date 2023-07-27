package com.demo.desh

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.demo.desh.navigation.NavGraph
import com.demo.desh.navigation.Screen
import com.demo.desh.model.User
import com.demo.desh.ui.theme.DeshprojectfeTheme
import com.demo.desh.ui.theme.nanum

class SurveyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = intent.getSerializableExtra("user") as User

        setContent {
            DeshprojectfeTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                )
                {}
                val navController= rememberNavController()
                NavGraph(navController)
            }
        }
    }
}

@Composable
fun SurveyScreen(navController: NavHostController) {
    val appBarText = "서비스 조사"
    val nextButtonText = "Next"

    //ToolbarWithMenu(name = appBarText)

    Spacer(modifier = Modifier.padding(horizontal = 90.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(35.dp))
        SurveyText()
        Spacer(modifier = Modifier.padding(start = 0.dp, 35.dp, 0.dp, 0.dp))
        Box(
            contentAlignment = Alignment.Center
        ) {
            CustomRadioGroup()
            Button(
                onClick = {navController.navigate(Screen.Survey2.route)},
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(44.dp)
                    .width(99.dp)
            ) {
                Text(
                    text = nextButtonText,
                    fontFamily = nanum,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

/*
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
*/

@Composable
private fun SurveyText() {
    val logoText = "Good Place"
    val firstGuideText = "상권 추천을 위해 설문에 답변해주세요!"
    val secondGuideText = "타겟층 연령을 선택하세요!"

    Text(text = logoText, fontFamily = nanum, fontSize = 23.sp, color = Color.Blue)

    Spacer(modifier = Modifier.padding(18.dp))

    Text(
        text = firstGuideText,
        fontFamily = nanum,
        fontSize = 15.sp,
        color = Color.Gray
    )

    Spacer(modifier = Modifier.padding(8.dp))

    Text(
        text = secondGuideText,
        fontFamily = nanum,
        fontSize = 17.sp,
        color = Color.Black
    )
}

@Composable
fun CustomRadioGroup() {
    val options = listOf(
        "10대",
        "20대",
        "30대",
        "40대",
    )
    var selectedOption by remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

            options.forEach { text ->
                Row(
                    modifier = Modifier
                        .padding(
                            all = 8.dp,
                        ),
                ) {
                    Text(
                        text = text,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(0.7f)
                            .clip(
                                shape = RoundedCornerShape(
                                    size = 12.dp,
                                ),
                            )
                            .clickable {
                                onSelectionChange(text)
                            }
                            .border(
                                1.5.dp,
                                if (text == selectedOption) {
                                    Color.Blue
                                } else {
                                    Color.Black
                                }, shape = RoundedCornerShape(12.dp)
                            )
                            .padding(
                                vertical = 12.dp,
                                horizontal = 16.dp,
                            ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
private fun showToast(context: Context, message: String){
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeshprojectfeTheme {
    }
}
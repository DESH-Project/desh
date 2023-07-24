package com.demo.desh
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.demo.desh.navigation.NavGraph
import com.demo.desh.navigation.Screen
import com.demo.desh.ui.theme.DeshprojectfeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DeshprojectfeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){}

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
//    val intent = Intent(context, SurveyActivity::class.java)

    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

//        Button(onClick = { context.startActivity(intent)}) {
//            Text(text = "설문조사")
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeshprojectfeTheme {
        Greeting("Android")
    }
}
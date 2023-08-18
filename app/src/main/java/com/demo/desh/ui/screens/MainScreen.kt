package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.demo.desh.util.MainNavigation

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center, modifier = Modifier.padding(0.dp,130.dp,0.dp,0.dp)
    ) {
        TopHeader(navController =navController)
        Spacer(modifier = Modifier.padding(0.dp,7.dp))
        MyUI("내가 본 매물")
        Spacer(modifier = Modifier.padding(0.dp,5.dp))
        HorizontalListView()
        Bottom(navController)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithMenu2() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Good place") },
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription ="매물 내놓기" )
                    }

                }
            )
        }
    ) {
        it.calculateTopPadding()
    }
}

@Composable
fun MyUI(name:String) {
    Row() {
        Divider(
            modifier = Modifier
                .padding(16.dp)
                .width(130.dp), color = Color.Black, thickness = 2.dp
        )
        Text(text = name)
        Divider(
            modifier = Modifier
                .padding(16.dp)
                .width(130.dp), color = Color.Black, thickness = 2.dp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bottom(navController: NavController){
    Scaffold(
        floatingActionButton = {
            Box(
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(MainNavigation.Map.route)
                    }) {
                    Text(text = "AI 추천받기")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,


        bottomBar = {
            BottomAppBar(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)

            ) {
            }
        },
    ) { }
}


@Composable
fun HorizontalListView() {
    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp)) {
        item{
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
                Text(text = "1")
            }

        }
        item{
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
                Text(text = "2")
            }

        }
        item{
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
                Text(text = "3")
            }

        }
        item{
            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
                Text(text = "4")
            }

        }
        item { Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "5")
        }
        }
        item { Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "6")
        }
        }
        item { Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "7")
        }
        }
        item { Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "8")
        }
        }
        item { Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "9")
        }
        }
        item { Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(10.dp), shape = RectangleShape) {
            Text(text = "10")
        }
        }
    }
}
@Composable
fun TopHeader(navController: NavController){
    Surface( color = Color.DarkGray,modifier = Modifier.fillMaxWidth().height(170.dp).clip(shape = CircleShape.copy(all = CornerSize(12.dp)))) {
        Surface(color = Color.White,modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp)))) {
            Button(
                onClick = { },
                shape = RectangleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "매몰내놓기")
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "건물주 되기")
            }
        }
    }
}



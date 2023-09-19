package com.demo.desh.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.model.PreviewInfo
import com.demo.desh.model.User
import com.demo.desh.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager



@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartScreen(
    user: User,
    viewModel: MainViewModel,
    goToMapScreen: () -> Unit
) {
    val testData = PreviewInfo.testData

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            Column {
                HorizontalPager(
                    count = testData.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(512.dp)
                ) { pageIndex ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = testData[pageIndex].imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .clickable { goToMapScreen() }
                        )

                        /*
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(12.dp)
                                .size(36.dp)
                                .alpha(if (pageIndex == 0) 0f else 0.65f)
                        )
                        */

                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(12.dp)
                                .size(36.dp)
                                .alpha(if (pageIndex == testData.size - 1) 0.1f else 0.45f)
                        )

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(12.dp)
                        ) {
                            val text = "${testData[pageIndex].deposit}/${testData[pageIndex].monthly}"
                            Text(
                                text = text,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Log.e("StartScreen", "pageIndex = $pageIndex")
                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "GOODPLACE",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 44.sp,
                        modifier = Modifier.padding(4.dp, 4.dp, 0.dp, 4.dp)
                    )

                    Row(modifier = Modifier.padding(4.dp, 4.dp, 0.dp, 4.dp)) {
                        Text(
                            text = user.nickname,
                            color = Color(0xFFFF6A68),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )

                        Text(
                            text = "님 반가워요!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    }

                    Column(modifier = Modifier.padding(4.dp, 12.dp, 0.dp, 4.dp)) {
                        Text(
                            text = "DESH는 소상공인을 위한 상권추천 서비스입니다.",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                        
                        Spacer(modifier = Modifier.padding(4.dp, 16.dp, 0.dp, 0.dp))

                        Text(
                            text = "아래 버튼을 눌러 지금 바로 시작해보세요!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }

                    Button(
                        onClick = goToMapScreen,
                        colors = ButtonDefaults.buttonColors(Color(0xFFFF6A68)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 40.dp, 8.dp, 0.dp)
                    ) {
                        Text(
                            text = "시작해보기",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ImageIndexBar(modifier: Modifier, size: Int, page: Int) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        for (now in 0 until size) {
            Divider(
                modifier = Modifier
                    .background(if (now == page) Color.White else Color.Gray)
                    .height(4.dp)
                    .width(36.dp)
                    .alpha(0.6f)
            )
            
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
}
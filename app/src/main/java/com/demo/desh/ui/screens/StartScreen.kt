package com.demo.desh.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.access.repository.MemberRepository
import com.demo.desh.model.PreviewInfo
import com.demo.desh.model.User
import com.demo.desh.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import java.text.DecimalFormat


@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartScreen(
    viewModel: MainViewModel,
    goToMapScreen: () -> Unit
) {
    val testData = PreviewInfo.testData

    LaunchedEffect(Unit) {
        viewModel.getLastMember()
    }

    val member by viewModel.member.observeAsState()
    val user = member?.let { User.toUser(it) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            HorizontalPager(
                count = testData.size,
                modifier = Modifier
                    .fillMaxSize()
            ) { pageIndex ->

                val lastPage = testData.size - 1 == pageIndex
                Log.e("StartScreen", "pageIndex : $pageIndex")

                AsyncImage(
                    model = testData[pageIndex].imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxHeight()
                )

                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(12.dp)
                        .size(36.dp)
                        .alpha(if (lastPage) 0f else 0.75f)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(24.dp)
                ) {

                    val df = DecimalFormat("##,###만원")
                    val dep = testData[pageIndex].deposit / 10000
                    val text = "${df.format(dep)} / ${testData[pageIndex].monthly}"

                    Text(
                        text = text,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (lastPage) {
                    Column(
                        modifier = Modifier
                            .padding(32.dp, 64.dp, 32.dp, 0.dp)
                            .align(Alignment.BottomCenter)
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
                                text = user?.nickname ?: "null",
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

                        Column(modifier = Modifier.padding(4.dp, 12.dp, 0.dp, 40.dp)) {
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
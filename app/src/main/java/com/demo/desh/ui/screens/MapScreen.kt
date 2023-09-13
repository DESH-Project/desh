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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.demo.desh.model.District
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.User
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import de.charlex.compose.BottomDrawerScaffold


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    user: User,
    viewModel: MainViewModel,
    goToRealtyDetail: (Long) -> Unit,
    goToRemainServiceListScreen: (Int) -> Unit
) {
    val serviceList by viewModel.serviceList.observeAsState()
    val recommendInfo by viewModel.recommendInfo.observeAsState()
    val districtInfo by viewModel.districtInfo.observeAsState()

    val onDistrictItemClick = { realtyId: Long -> goToRealtyDetail(realtyId) }
    val onServiceItemClick = { serviceName: String -> viewModel.fetchMapView(serviceName) }
    val onDistrictButtonClick = { districtName: String -> viewModel.fetchDistrictInfoList(districtName)}
    val onListMoreButtonClick = { index: Int -> goToRemainServiceListScreen(index)  }

    LaunchedEffect(Unit) {
        viewModel.fetchMapView()
        viewModel.fetchServiceList()
    }

    // https://github.com/ch4rl3x/BottomDrawerScaffold    -->   BottomDrawerScaffold Library
    // https://stackoverflow.com/questions/67854169/how-to-implement-bottomappbar-and-bottomdrawer-pattern-using-android-jetpack-com
    // https://developersbreach.com/modal-bottom-sheet-jetpack-compose-android/
    BottomDrawerScaffold(
        drawerContent = {
            DrawerContent(
                user = user,
                serviceList = serviceList,
                districtInfo = districtInfo,
                recommendInfo = recommendInfo,
                onServiceItemClick = onServiceItemClick,
                onListMoreButtonClick = onListMoreButtonClick,
                onDistrictButtonClick = onDistrictButtonClick,
                onDistrictItemClick = onDistrictItemClick
            )
        },
        drawerGesturesEnabled = true,
        drawerBackgroundColor = Color(0xAA000000),  //Transparent drawer for custom Drawer shape
        drawerElevation = 0.dp,

        content = {
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AndroidView(
                        factory = { context -> MapViewManager.createMapView(context, recommendInfo) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    )
}

@Composable
private fun DrawerContent(
    user: User,
    serviceList: ServerResponse<String>?,
    districtInfo: ServerResponse<District>?,
    recommendInfo: ServerResponse<Recommend>?,
    onServiceItemClick: (String) -> Unit,
    onListMoreButtonClick: (Int) -> Unit,
    onDistrictButtonClick: (String) -> Unit,
    onDistrictItemClick: (Long) -> Unit
) {
    val placeHolderColor = Color(0xAA000000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))

        Divider(modifier = Modifier
            .background(color = Color.White)
            .height(4.dp)
            .width(36.dp)
            .alpha(0.6f)
        )

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))

        CreateListButton(serviceList, onServiceItemClick, onListMoreButtonClick)

        Spacer(modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = user.nickname,
                    color = Color(0xFFFF6A68),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Text(
                    text = "님을 위한 추천 상권목록",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            CreateRecommendPager(recommendInfo, onDistrictButtonClick)
        }

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = user.nickname,
                    color = Color(0xFFFF6A68),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Text(
                    text = "님을 위한 추천 상가목록",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            CreateDistrictPager(districtInfo, onDistrictItemClick, placeHolderColor)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CreateRecommendPager(
    recommendInfo: ServerResponse<Recommend>?,
    onDistrictButtonClick: (String) -> Unit
) {
    val count = recommendInfo?.size ?: 0
    val list = recommendInfo?.data ?: listOf()

    HorizontalPager(
        count = count,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(48.dp)
    ) { page ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .clickable { onDistrictButtonClick(list[page].district) }
        ) {
            val item = list[page]

            Text(
                text = item.service,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.district,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.predict.toString(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CreateDistrictPager(
    districtInfo: ServerResponse<District>?,
    onDistrictItemClick: (Long) -> Unit,
    placeHolderColor : Color
) {
    val count = districtInfo?.size ?: 0
    val list = districtInfo?.data ?: listOf()

    HorizontalPager(
        count = count,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) { page ->
        val item = list[page]

        Column(
            modifier = Modifier.clickable { onDistrictItemClick(item.id) }
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(placeHolderColor),
                modifier = Modifier.fillMaxWidth()
            )

            Column {
                Text(text = "주소: ${item.address}")
                Text(text = "시세: ${item.price}")
            }
        }
    }
}



@Composable
private fun CreateListButton(
    serviceList: ServerResponse<String>?,
    onListButtonClick: (String) -> Unit,
    onListMoreButtonClick: (Int) -> Unit
) {
    val index = 3
    val previewServiceList = serviceList?.data?.slice(0 until index)?.toMutableList()

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        previewServiceList?.forEach { item ->
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = 8.dp,
                modifier = Modifier
                    .width(84.dp)
                    .height(48.dp)
                    .padding(4.dp),
                backgroundColor = Color(0xFF444548)
            ) {
                TextButton(onClick = {
                    Log.e("MapScreen.CreateListButton", "Click item = $item")
                    onListButtonClick(item)
                }) {
                    Text(text = item, color = Color.White)
                }
            }
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = 8.dp,
            modifier = Modifier
                .width(84.dp)
                .height(48.dp)
                .padding(4.dp),
            backgroundColor = Color(0xFF444548)
        ) {
            IconButton(onClick = { onListMoreButtonClick(index) } ) {
                Icon(Icons.Filled.KeyboardArrowRight, null)
            }
        }
    }
}


@Composable
private fun InfoTextBar(
    infoText: String?,
    serviceList: ServerResponse<String>?,
    recommendInfo: ServerResponse<Recommend>?,
    onBackButtonClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackButtonClick,
            modifier = Modifier
                .background(Color.Transparent)
                .width(40.dp)
                .padding(15.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "뒤로가기")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 15.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "조회 가능한 업종의 개수는 ${serviceList?.size ?: 0}개 입니다.", fontWeight = FontWeight.Bold)
            Text(text = "\"${infoText}\"에 대한 검색 결과입니다.")
            Text(text = "총 ${recommendInfo?.size ?: 0}개의 결과가 검색되었습니다")
        }
    }
}

package com.demo.desh.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.demo.desh.MainActivity
import com.demo.desh.model.District
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.MainViewModel
import de.charlex.compose.BottomDrawerScaffold


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    viewModel: MainViewModel,
    goToRealtyDetail: (Long) -> Unit,
    markerEventListener: MainActivity.MarkerEventListener
) {
    val serviceList by viewModel.serviceList.observeAsState()
    val mv by viewModel.mapView.observeAsState()
    val recommendInfo by viewModel.recommendInfo.observeAsState()
    val infoText by viewModel.infoText.observeAsState()
    val districtInfo by viewModel.districtInfo.observeAsState()
    val showBottomDrawer by viewModel.showBottomDrawer.observeAsState()

    val onDrawerItemClick = { realtyId: Long -> goToRealtyDetail(realtyId) }
    val onListButtonClick = { serviceName: String ->
        viewModel.fetchMapView(serviceName)
        viewModel.noShowBottomDrawer()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchMapView()
        viewModel.fetchServiceList()
    }

    // https://github.com/ch4rl3x/BottomDrawerScaffold    -->   BottomDrawerScaffold Library
    // https://stackoverflow.com/questions/67854169/how-to-implement-bottomappbar-and-bottomdrawer-pattern-using-android-jetpack-com
    // https://developersbreach.com/modal-bottom-sheet-jetpack-compose-android/
    BottomDrawerScaffold(
        drawerContent = {
            if (showBottomDrawer == true) {
                DrawerContent(serviceList, districtInfo, onListButtonClick, onDrawerItemClick)
            }
        },
        drawerGesturesEnabled = true,
        drawerPeekHeight = if (showBottomDrawer == true) 150.dp else 24.dp,
        drawerBackgroundColor = Color.LightGray,  //Transparent drawer for custom Drawer shape
        drawerElevation = 3.dp,

        content = {
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AndroidView(
                        factory = mv ?: MapViewManager.createMapView(recommendInfo),
                        modifier = Modifier.fillMaxSize(),
                        update = { mv ->
                            MapViewManager.onMapViewUpdate(
                                mv,
                                recommendInfo,
                                markerEventListener
                            )
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DrawerContent(
    serviceList: ServerResponse<String>?,
    districtInfo: ServerResponse<District>?,
    onListButtonClick: (String) -> Unit,
    onItemClick: (Long) -> Unit
) {
    val placeHolderColor = Color(0x33000000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))

        Divider(modifier = Modifier
            .background(color = Color.Gray)
            .height(4.dp)
            .width(24.dp)
        )

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))
        
        LazyRow {
            itemsIndexed(serviceList?.data ?: listOf()) { _, item ->
                Card(
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(4.dp),
                    backgroundColor = Color.Gray
                ) {
                    TextButton(onClick = {
                        Log.e("MapScreen.CreateListButton", "Click item = $item")
                        onListButtonClick(item)
                    }) {
                        Text(text = item, color = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(districtInfo?.data ?: listOf()) { _, item ->
                Card(
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    onClick = { onItemClick(item.id) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        AsyncImage(
                            model = item.image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            placeholder = ColorPainter(placeHolderColor),
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.size(8.dp))

                        Column {
                            Text(text = "주소: ${item.address}")
                            Text(text = "시세: ${item.price}")
                            Spacer(modifier = Modifier.size(4.dp))
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun CreateListButton(
    serviceList: ServerResponse<String>?,
    onSelectedServiceNameChange: (String) -> Unit
) {
    LazyRow {
        itemsIndexed(serviceList?.data?: listOf()) { _, item ->
            Card {
                TextButton(onClick = {
                    Log.e("MapScreen.CreateListButton", "Click item = $item")
                    onSelectedServiceNameChange(item)
                }) {
                    Text(text = item)
                }
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

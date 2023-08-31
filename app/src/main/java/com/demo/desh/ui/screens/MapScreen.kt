package com.demo.desh.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerState
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.demo.desh.MainActivity
import com.demo.desh.model.District
import com.demo.desh.model.DistrictInfo
import com.demo.desh.model.RecommendInfo
import com.demo.desh.model.ServiceList
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    viewModel: MainViewModel,
    onBackButtonClick: () -> Unit,
    markerEventListener: MainActivity.MarkerEventListener
) {
    val serviceList by viewModel.serviceList.observeAsState()
    val mv by viewModel.mapView.observeAsState()
    val recommendInfo by viewModel.recommendInfo.observeAsState()
    val infoText by viewModel.infoText.observeAsState()
    val districtInfo by viewModel.districtInfo.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMapView()
        viewModel.fetchServiceList()
    }

    val (gesturesEnabled, toggleGesturesEnabled) = remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)

    BottomDrawer(
        gesturesEnabled = gesturesEnabled,
        drawerState = drawerState,
        drawerContent = { DistrictDrawerContent(districtInfo) },
        content = {
            Scaffold(
                topBar = {
                    Column {
                        CreateListButton(serviceList) { viewModel.fetchMapView(it) }
                        InfoTextBar(infoText, serviceList, recommendInfo, onBackButtonClick)
                        BottomBarContent(coroutineScope = coroutineScope, drawerState = drawerState)
                    }
                },

                bottomBar = { BottomBarContent(coroutineScope = coroutineScope, drawerState = drawerState) },

                content = { innerPadding ->
                    val modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxHeight()

                    AndroidView(
                        factory = mv ?: MapViewManager.createMapView(recommendInfo),
                        modifier = Modifier.fillMaxSize(),
                        update = { mv -> MapViewManager.onMapViewUpdate(mv, recommendInfo, markerEventListener) }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomBarContent(coroutineScope: CoroutineScope, drawerState: BottomDrawerState) {
    BottomAppBar {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
            IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Localized Description")
            }
        }

        Spacer(modifier = Modifier.weight(1f, true))

        IconButton(onClick = { }) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Localized Description")
        }
    }
}

@Composable
private fun TestDistrictList(districtInfo: DistrictInfo?) {


    /*
    LazyRow {
        itemsIndexed(districtInfo?.list ?: listOf("Loading...")) { idx, item ->
            Text(text = "$idx : $item")
        }
    }
    */
}

@Composable
private fun DistrictDrawerContent(districtInfo: DistrictInfo?) {
    LazyColumn {
        itemsIndexed(districtInfo?.list ?: listOf<District>()) { _, item ->
            Card {
                Image(painter = rememberAsyncImagePainter(model = item.image), contentDescription = null)
                Text(text = item.address)
                Text(text = item.price.toString())
            }
        }
    }
}

@Composable
private fun CreateListButton(
    serviceList: ServiceList?,
    onSelectedServiceNameChange: (String) -> Unit
) {
    LazyRow {
        itemsIndexed(serviceList?.list ?: listOf("Loading...")) { _, item ->
            Card {
                TextButton(onClick = { onSelectedServiceNameChange(item) }) {
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
private fun InfoTextBar(
    infoText: String?,
    serviceList: ServiceList?,
    recommendInfo: RecommendInfo?,
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

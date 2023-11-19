package com.demo.desh.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.R
import com.demo.desh.model.RealtyPreview
import com.demo.desh.model.RecommendDistrict
import com.demo.desh.model.User
import com.demo.desh.ui.theme.DefaultBackgroundColor
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.RoomViewModel
import com.demo.desh.viewModel.UserViewModel
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapReadyCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles

@Composable
fun HomeScreen(
    userViewModel: UserViewModel,
    roomViewModel: RoomViewModel,
    goToRealtyDetail: (Long) -> Unit,
    goToProfileScreen: (Long) -> Unit,
    goToChatListScreen: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        userViewModel.fetchServiceList()
        userViewModel.loadRecommendDistrict()
        roomViewModel.findLocalUser()
    }

    /* STATES */
    val user by roomViewModel.user.observeAsState()
    val serviceList by userViewModel.serviceList.observeAsState(initial = mapOf())
    val recommendDistrictList by userViewModel.recommendDistrictList.observeAsState()
    val nearbyStoreList by userViewModel.nearbyStoreList.observeAsState(initial = listOf())

    var selectedServiceName by rememberSaveable { mutableStateOf("전체") }
    var selectedDistrictLatLng by rememberSaveable { mutableStateOf(LatLng.from(37.394660, 127.111182)) }

    /* HANDLERS */
    val onServiceItemClick = { serviceName: String ->
        userViewModel.loadRecommendDistrict(serviceName)
        selectedServiceName = serviceName
    }

    val onSelectedLatLngChanged = { loc: LatLng -> selectedDistrictLatLng = loc }

    user?.let {
        val onDistrictButtonClick = { districtName: String -> userViewModel.fetchNearbyStores(districtName) }
        val onStoreButtonClick = { storeId: Long -> goToRealtyDetail(storeId) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            RecommendContent(
                user = user!!,
                serviceList = serviceList,
                selectedServiceName = selectedServiceName,
                onServiceItemClick = onServiceItemClick,
                onSelectedLatLngChanged = onSelectedLatLngChanged,
                recommendDistrictList = recommendDistrictList ?: listOf(),
                onDistrictButtonClick = onDistrictButtonClick,
                nearbyStoreList = nearbyStoreList,
                onStoreButtonClick = onStoreButtonClick
            )

            MapBox(selectedDistrictLatLng)
        }
    }
}

@Composable
fun RecommendContent(
    user: User,
    serviceList: Map<String, List<String>>,
    selectedServiceName: String,
    onServiceItemClick: (String) -> Unit,
    onSelectedLatLngChanged: (LatLng) -> Unit,
    recommendDistrictList: List<RecommendDistrict> = listOf(),
    onDistrictButtonClick: (String) -> Unit,
    nearbyStoreList: List<RealtyPreview>,
    onStoreButtonClick: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .background(DefaultBackgroundColor)
            .fillMaxSize()
    ) {
        ConstraintLayout {
            val (userIntroRef, serviceListButtonsRef, districtCardRef, nearbyStoreRef) = createRefs()

            UserIntroText(
                user = user,
                selectedServiceName = selectedServiceName,
                modifier = Modifier
                    .constrainAs(userIntroRef) {
                        top.linkTo(anchor = parent.top, margin = 24.dp)
                        start.linkTo(anchor = parent.start, margin = 12.dp)
                    }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            ServiceListButtons(
                serviceList = serviceList,
                onServiceItemClick = onServiceItemClick,
                modifier = Modifier
                    .constrainAs(serviceListButtonsRef) {
                        top.linkTo(anchor = userIntroRef.bottom, margin = 36.dp)
                        start.linkTo(anchor = parent.start, margin = 12.dp)
                    }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            RecommendDistrictCards(
                districtList = recommendDistrictList,
                onDistrictButtonClick = onDistrictButtonClick,
                onSelectedLatLngChanged = onSelectedLatLngChanged,
                modifier = Modifier
                    .constrainAs(districtCardRef) {
                        top.linkTo(anchor = serviceListButtonsRef.bottom, margin = 36.dp)
                        start.linkTo(anchor = parent.start, margin = 12.dp)
                    }
            )

            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun UserIntroText(
    user: User,
    selectedServiceName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = user.nickname,
                color = HighlightColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = "님",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Row {
            Text(
                text = selectedServiceName,
                color = HighlightColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = "에 대한 추천 상권 정보를 가져왔어요.",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun ServiceListButtons(
    serviceList: Map<String, List<String>>,
    onServiceItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keys = serviceList.keys

    var subBtns by remember { mutableStateOf(listOf<String>()) }
    val onKeySelect = { key: String ->
        val subs = serviceList[key]
        Log.e("subs", subs.toString())

        if (!subs.isNullOrEmpty()) {
            subBtns = subs
        }
    }

    Column(modifier = modifier) {
        Text(text = "다른 상권도 찾아보세요!", color = Color.LightGray, fontSize = 12.sp)
        
        LazyRow {
            items(keys.toList()) { key ->
                Button(onClick = { onKeySelect(key) }) {
                    Text(text = key)
                }

                Spacer(modifier = Modifier.padding(4.dp))
            }
        }

        LazyRow {
            items(subBtns) { item ->
                Button(
                    onClick = { onServiceItemClick(item) },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = item, color = Color.White)
                }

                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun RecommendDistrictCards(
    districtList: List<RecommendDistrict>,
    onDistrictButtonClick: (String) -> Unit,
    onSelectedLatLngChanged: (LatLng) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(districtList) {
            Card(modifier = Modifier.clickable {
                onDistrictButtonClick(it.district)
                onSelectedLatLngChanged(LatLng.from(it.lat, it.lng))
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = it.district)
                    Text(text = it.service)
                    Text(text = it.predict.toString())
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun NearbyStoreCards(
    nearbyStoreList: List<RealtyPreview>,
    onStoreButtonClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        if (nearbyStoreList.isEmpty()) {
            Text(
                text = "아직 등록된 상가가 없어요",
                fontSize = 20.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        LazyRow {
            items(nearbyStoreList) {
                Card(modifier = Modifier.clickable { onStoreButtonClick(it.realtyId) }) {
                    Column {
                        AsyncImage(model = it.previewImage.first(), contentDescription = null)
                        Text(text = "${it.deposit}/${it.monthlyRental}")
                        Text(text = it.star.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun MapBox(loc: LatLng) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .padding(50.dp),
    ) {
        AndroidView(
            factory = { context: Context -> MapViewManager.createMapView(context, loc) },
            update = { mv: MapView -> MapViewManager.createMapView(context, loc) }
        )
    }
}

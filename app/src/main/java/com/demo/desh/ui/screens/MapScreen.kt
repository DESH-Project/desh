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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.model.RealtyPreview
import com.demo.desh.model.RecommendDistrict
import com.demo.desh.model.User
import com.demo.desh.ui.TopBarContent
import com.demo.desh.ui.theme.DefaultBackgroundColor
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.UserViewModel
import com.kakao.vectormap.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    userId: Long,
    userViewModel: UserViewModel,
    goToRealtyDetailScreen: (Long, Long) -> Unit,
    goToProfileScreen: (Long) -> Unit,
    goToChatListScreen: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        userViewModel.fetchServiceList()
        userViewModel.getUserInfo()
        userViewModel.loadRecommendDistrict()
    }

    /* STATES */
    val user by userViewModel.targetUser.observeAsState()
    val serviceList by userViewModel.serviceList.observeAsState(initial = mapOf())
    val recommendDistrictList by userViewModel.recommendDistrictList.observeAsState(initial = listOf())
    val nearbyStoreList by userViewModel.nearbyStoreList.observeAsState(initial = listOf())

    var selectedServiceName by rememberSaveable { mutableStateOf("전체") }

    /* HANDLERS */
    val onServiceItemClick = { serviceName: String ->
        userViewModel.loadRecommendDistrict(serviceName)
        selectedServiceName = serviceName
    }

    val onDistrictButtonClick = { districtName: String -> userViewModel.fetchNearbyStores(districtName) }
    val onStoreButtonClick = { storeId: Long -> goToRealtyDetailScreen(userId, storeId) }

    user?.let {
        BottomSheetScaffold(
            scaffoldState = rememberBottomSheetScaffoldState(),

            topBar = {
                TopBarContent(
                    goToProfileScreen = { goToProfileScreen(userId) },
                    goToChatListScreen = { goToChatListScreen(userId) },
                    goToRealtyDetailScreen = { goToRealtyDetailScreen(userId, 1L) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DefaultBackgroundColor)
                )
            },

            sheetContent = {
                DrawerContent(
                    user = user!!,
                    serviceList = serviceList,
                    selectedServiceName = selectedServiceName,
                    onServiceItemClick = onServiceItemClick,
                    recommendDistrictList = recommendDistrictList,
                    onDistrictButtonClick = onDistrictButtonClick,
                    nearbyStoreList = nearbyStoreList,
                    onStoreButtonClick = onStoreButtonClick
                )
            },

            drawerGesturesEnabled = true,
            drawerBackgroundColor = DefaultBackgroundColor,

            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        AndroidView(
                            factory = { context -> MapViewManager.createMapView(context) },
                            modifier = Modifier.fillMaxSize(),
                            update = { mapView: MapView -> MapViewManager.labelingOnMapView(mapView, recommendDistrictList) }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(
    user: User,
    serviceList: Map<String, List<String>>,
    selectedServiceName: String,
    onServiceItemClick: (String) -> Unit,
    recommendDistrictList: List<RecommendDistrict>,
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
                modifier = Modifier
                    .constrainAs(districtCardRef) {
                        top.linkTo(anchor = serviceListButtonsRef.bottom, margin = 36.dp)
                        start.linkTo(anchor = parent.start, margin = 12.dp)
                    }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            NearbyStoreCards(
                nearbyStoreList = nearbyStoreList,
                onStoreButtonClick = onStoreButtonClick,
                modifier = Modifier
                    .constrainAs(nearbyStoreRef) {
                        top.linkTo(anchor = districtCardRef.bottom, margin = 26.dp)
                        start.linkTo(anchor = parent.start, margin = 12.dp)
                    }
            )
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
                Button(onClick = { onServiceItemClick(item) }) {
                    Text(text = item)
                }
            }
        }
    }
}

@Composable
fun RecommendDistrictCards(
    districtList: List<RecommendDistrict>,
    onDistrictButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(districtList) {
            Card(modifier = Modifier.clickable { onDistrictButtonClick(it.district) }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = it.district)
                    Text(text = it.service)
                    Text(text = it.predict.toString())
                    Text(text = "(${it.lat}, ${it.lng})")
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
                Card(modifier = Modifier.clickable { onStoreButtonClick(it.id) }) {
                    Column {
                        AsyncImage(model = it.previewImage.first(), contentDescription = null)
                        Text(text = "${it.deposit}/${it.monthlyRental}")
                        Text(text = it.address)
                        Text(text = it.star.toString())
                    }
                }
            }
        }
    }

}

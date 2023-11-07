package com.demo.desh.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.model.District
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.User
import com.demo.desh.ui.CustomFullDivideLine
import com.demo.desh.ui.CustomIconMenu
import com.demo.desh.ui.theme.DefaultBackgroundColor
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kakao.vectormap.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    userViewModel: UserViewModel,
    goToRealtyDetailScreen: (Long) -> Unit,
    goToProfileScreen: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        userViewModel.fetchServiceList()
    }

    /* STATES */
    val user by userViewModel.user.observeAsState()
    val serviceList by userViewModel.serviceList.observeAsState()
    val recommendInfo by userViewModel.recommendInfo.observeAsState()
    val districtInfo by userViewModel.districtInfo.observeAsState()
    val selectedServiceName by userViewModel.selectedServiceName.observeAsState()

    val onServiceItemClick = { serviceName: String -> userViewModel.fetchMapView(serviceName) }
    val onDistrictItemClick = { realtyId: Long -> goToRealtyDetailScreen(realtyId) }
    val onDistrictButtonClick = { districtName: String -> userViewModel.fetchDistrictInfoList(districtName) }
    val goToProfileScreenWithUser = { userId: Long -> goToProfileScreen(userId) }

    if (user == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
        ) {
            CircularProgressIndicator()
        }
    }

    BottomSheetScaffold(
        topBar = {
            TopMapBar(
                serviceList = serviceList?.data ?: mapOf(),
                user = user,
                onServiceItemClick = onServiceItemClick,
                goToProfileScreenWithUser = goToProfileScreenWithUser
            )
         },

        sheetContent = {
            DrawerContent(
                user = user,
                recommendInfo = recommendInfo,
                districtInfo = districtInfo,
                selectedServiceName = selectedServiceName,
                onDistrictButtonClick = onDistrictButtonClick,
                onDistrictItemClick = onDistrictItemClick
            )
        },

        drawerGesturesEnabled = true,
        drawerBackgroundColor = DefaultBackgroundColor,
        drawerElevation = 0.dp,

        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    AndroidView(
                        factory = { context -> MapViewManager.createMapView(context) },
                        modifier = Modifier.fillMaxSize(),
                        update = { mapView: MapView -> MapViewManager.labelingOnMapView(mapView, recommendInfo) }
                    )
                }
            }
        }
    )
}

@Composable
fun TopMapBar(
    serviceList: Map<String, List<String>>,
    user: User?,
    onServiceItemClick: (String) -> Unit,
    goToProfileScreenWithUser: (Long) -> Unit
) {
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val onSheetClose = { showSheet = false }

    if (showSheet) {
        BottomSheet(onDismiss = { showSheet = false }) {
            BottomSheetContent(serviceList, onServiceItemClick, onSheetClose)
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showSheet = true }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }

            Text(
                text = "${serviceList.size}",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = HighlightColor
            )

            Text(
                text = "개의 업종을 확인해보세요.",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            CustomIconMenu(
                vector = Icons.Default.AccountCircle,
                onIconClick = { goToProfileScreenWithUser(user?.id!!) },
                tint = Color.DarkGray
            )
        }
    }
}

@Composable
fun BottomSheetContent(
    serviceList: Map<String, List<String>>,
    onServiceItemClick: (String) -> Unit,
    onSheetClose: () -> Unit
) {
    var subButtons by rememberSaveable { mutableStateOf<List<String>>(listOf()) }
    val offset = 3

    val onCategoryClick = { key: String ->
        subButtons = serviceList[key] ?: listOf()
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            text = "카테고리를 선택해주세요",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(bottom = 24.dp))

        val list = serviceList.keys.toList()

        for (i in list.indices step offset) {
            Row {
                var j = i

                while (j < i + 3 && j < list.size) {
                    TextButton(
                        onClick = { onCategoryClick(list[j]) },
                        modifier = Modifier
                            .height(70.dp)
                            .padding(4.dp)
                            .border(BorderStroke(1.dp, Color.Black), shape = CircleShape)
                            .background(Color.Green)
                    ) {
                        Text(
                            text = list[j],
                            color = Color.DarkGray,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }

                    j++
                }
            }
        }

        LazyColumn {
            items(subButtons) { item ->
                TextButton(onClick = {
                    onServiceItemClick(item)
                    onSheetClose()
                }) {
                    Text(text = item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismiss: () -> Unit,
    items: @Composable () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        containerColor = DefaultBackgroundColor,
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        items()
    }
}

@Composable
fun DrawerContent(
    user: User?,
    recommendInfo: ServerResponse<Recommend>?,
    districtInfo: ServerResponse<District>?,
    selectedServiceName: String?,
    onDistrictButtonClick: (String) -> Unit,
    onDistrictItemClick: (Long) -> Unit
) {
    /* Custom Colors */
    val phc = Color(0xAA000000)   // Place Holder Color

    /* Custom Size */
    // Text
    val dts = 16.sp  // Default Text Size
    val lts = 20.sp  // Large Text Size

    // Padding
    val sps = 8.dp   // Small Padding Size
    val dps = 16.dp  // Default Padding Size

    Box(modifier = Modifier.background(DefaultBackgroundColor)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (remainsMarginRef) = createRefs()

            if (recommendInfo != null) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(modifier = Modifier.padding(dps)) {
                        Text(
                            text = "\'${user?.nickname}\'",
                            color = HighlightColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = lts
                        )

                        Text(
                            text = "님을 위한 추천 상권!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = lts
                        )
                    }

                    RecommendInfoText(
                        selectedServiceName = selectedServiceName,
                        recommendInfoSize = recommendInfo.size,
                        fontSize = dts,
                        textColor = Color.White,
                        highlightTextColor = HighlightColor,
                        paddingSize = dps
                    )

                    CreateRecommendPager(recommendInfo, dts, onDistrictButtonClick)
                }

                CustomFullDivideLine()
            }

            if (districtInfo != null) {
                Spacer(modifier = Modifier.padding(dps))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    CreateDistrictPager(districtInfo, onDistrictItemClick, phc)
                }
            }

            // 마지막 여백
            Spacer(modifier = Modifier.constrainAs(remainsMarginRef) {
                bottom.linkTo(anchor = parent.bottom, margin = 60.dp)
            })
        }
    }
}

@Composable
fun RecommendInfoText(
    selectedServiceName: String?,
    recommendInfoSize: Int,
    fontSize: TextUnit,
    textColor: Color,
    highlightTextColor: Color,
    paddingSize: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingSize),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "\'${selectedServiceName}\'",
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            color = highlightTextColor
        )

        Row {
            Text(
                text = "$recommendInfoSize",
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                color = highlightTextColor
            )

            Text(
                text = "개의 상권 검색 결과",
                fontWeight = FontWeight.Bold,
                fontSize = fontSize,
                color = textColor
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CreateRecommendPager(
    recommendInfo: ServerResponse<Recommend>,
    textSize: TextUnit,
    onDistrictButtonClick: (String) -> Unit
) {
    val count = recommendInfo.size
    val list = recommendInfo.data

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
            modifier = Modifier.clickable { onDistrictButtonClick(list[page].district) }
        ) {
            val item = list[page]

            Text(
                text = item.district,
                color = Color.White,
                fontSize = textSize,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = item.predict.toString(),
                color = Color.White,
                fontSize = textSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CreateDistrictPager(
    districtInfo: ServerResponse<District>,
    onDistrictItemClick: (Long) -> Unit,
    placeHolderColor : Color
) {
    val count = districtInfo.size
    val list = districtInfo.data

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


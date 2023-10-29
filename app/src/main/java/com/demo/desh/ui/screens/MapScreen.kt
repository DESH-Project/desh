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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ButtonDefaults

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import com.demo.desh.ui.CustomIconMenu
import com.demo.desh.ui.theme.DefaultBackgroundColor
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.UserViewModel
import com.kakao.vectormap.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    userViewModel: UserViewModel,
    goToRealtyDetail: (Long) -> Unit,
) {
    LaunchedEffect(Unit) {
        userViewModel.fetchServiceList()
    }

    val user = userViewModel.user.value

    /* STATES */
    val serviceList by userViewModel.serviceList.observeAsState()
    val recommendInfo by userViewModel.recommendInfo.observeAsState()
    val districtInfo by userViewModel.districtInfo.observeAsState()
    val selectedServiceName by userViewModel.selectedServiceName.observeAsState()

    val onServiceItemClick = { serviceName: String -> userViewModel.fetchMapView(serviceName) }
    val onDistrictItemClick = { realtyId: Long -> goToRealtyDetail(realtyId) }
    val onDistrictButtonClick = { districtName: String -> userViewModel.fetchDistrictInfoList(districtName) }


    BottomSheetScaffold(
        topBar = {
            TopMapBar(
                serviceList = serviceList?.data ?: mapOf(),
                onServiceItemClick = onServiceItemClick
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
    onServiceItemClick: (String) -> Unit
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
                onIconClick = { },
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


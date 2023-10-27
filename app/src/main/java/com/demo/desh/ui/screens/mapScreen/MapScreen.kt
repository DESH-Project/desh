package com.demo.desh.ui.screens.mapScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.UserViewModel
import com.kakao.vectormap.MapView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    userViewModel: UserViewModel,
    goToRealtyDetail: (Long) -> Unit,
) {
    val user = userViewModel.user.value

    /* STATES */
    val serviceList by userViewModel.serviceList.observeAsState()
    val recommendInfo by userViewModel.recommendInfo.observeAsState()
    val districtInfo by userViewModel.districtInfo.observeAsState()
    val selectedServiceName by userViewModel.selectedServiceName.observeAsState()

    val onServiceItemClick = { serviceName: String -> userViewModel.fetchMapView(serviceName) }
    val onDistrictItemClick = { realtyId: Long -> goToRealtyDetail(realtyId) }
    val onDistrictButtonClick = { districtName: String -> userViewModel.fetchDistrictInfoList(districtName) }

    val defaultBackgroundColor = Color(0xAA000000)

    LaunchedEffect(Unit) {
        userViewModel.fetchServiceList()
    }

    // https://github.com/ch4rl3x/BottomDrawerScaffold --> BottomDrawerScaffold Library
    // https://stackoverflow.com/questions/67854169/how-to-implement-bottomappbar-and-bottomdrawer-pattern-using-android-jetpack-com
    // https://developersbreach.com/modal-bottom-sheet-jetpack-compose-android/
    BottomSheetScaffold(
        sheetContent = {
            DrawerContent(
                user = user,
                serviceList = serviceList,
                recommendInfo = recommendInfo,
                districtInfo = districtInfo,
                selectedServiceName = selectedServiceName,
                defaultBackgroundColor = defaultBackgroundColor,
                onServiceItemClick = onServiceItemClick,
                onDistrictButtonClick = onDistrictButtonClick,
                onDistrictItemClick = onDistrictItemClick
            )
        },

        drawerGesturesEnabled = true,
        drawerBackgroundColor = defaultBackgroundColor,
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



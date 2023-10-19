package com.demo.desh.ui.screens.mapScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
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
import de.charlex.compose.BottomDrawerScaffold

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    userViewModel: UserViewModel,
    goToRealtyDetail: (Long) -> Unit,
) {
    val serviceList by userViewModel.serviceList.observeAsState()
    val recommendInfo by userViewModel.recommendInfo.observeAsState()
    val districtInfo by userViewModel.districtInfo.observeAsState()
    val user by userViewModel.user.observeAsState()
    val selectedServiceName by userViewModel.selectedServiceName.observeAsState()

    val onServiceItemClick = { serviceName: String -> userViewModel.fetchMapView(serviceName) }
    val onDistrictItemClick = { realtyId: Long -> goToRealtyDetail(realtyId) }
    val onDistrictButtonClick = { districtName: String -> userViewModel.fetchDistrictInfoList(districtName)}

    val defaultBackgroundColor = Color(0xAA000000)

    LaunchedEffect(Unit) {
        userViewModel.fetchServiceList()
    }

    // https://github.com/ch4rl3x/BottomDrawerScaffold --> BottomDrawerScaffold Library
    // https://stackoverflow.com/questions/67854169/how-to-implement-bottomappbar-and-bottomdrawer-pattern-using-android-jetpack-com
    // https://developersbreach.com/modal-bottom-sheet-jetpack-compose-android/
    BottomDrawerScaffold(
        drawerContent = {
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
        drawerPeekHeight = 96.dp,

        content = {
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Column {
                        /*
                        SearchableTopBar(
                            modifier = Modifier
                                .padding(16.dp, 36.dp, 16.dp, 0.dp)
                                .background(Color.Transparent),

                            searchMode = searchMode ?: false,
                            searchText = searchText ?: "",
                            onSearchTextChanged = { userViewModel.fetchSearchText(it) },
                            onSearchButtonClicked = { userViewModel.fetchSearchModeTrue() }
                        )
                        */

                        AndroidView(
                            factory = { context -> MapViewManager.createMapView(context) },
                            modifier = Modifier.fillMaxSize(),
                            update = { mv: MapView -> MapViewManager.labelingOnMapView(mv, recommendInfo) }
                        )
                    }
                }
            }
        }
    )
}



package com.demo.desh.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import com.demo.desh.util.MapViewManager
import com.demo.desh.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.kakao.vectormap.MapView
import de.charlex.compose.BottomDrawerScaffold


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    viewModel: MainViewModel,
    goToRealtyDetail: (Long) -> Unit,
) {
    val serviceList by viewModel.serviceList.observeAsState()
    val recommendInfo by viewModel.recommendInfo.observeAsState()
    val districtInfo by viewModel.districtInfo.observeAsState()

    val onDistrictItemClick = { realtyId: Long -> goToRealtyDetail(realtyId) }
    val onServiceItemClick = { serviceName: String -> viewModel.fetchMapView(serviceName) }
    val onDistrictButtonClick = { districtName: String -> viewModel.fetchDistrictInfoList(districtName)}

    LaunchedEffect(Unit) {
        viewModel.fetchServiceList()
        viewModel.getLastMember()
    }

    val member by viewModel.member.observeAsState()
    val user = member?.let { User.toUser(it) }

    val searchMode by viewModel.searchMode.observeAsState()
    val searchText by viewModel.searchText.observeAsState()

    BackHandler { viewModel.fetchSearchModeFalse() }

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
                onDistrictButtonClick = onDistrictButtonClick,
                onDistrictItemClick = onDistrictItemClick
            )
        },

        drawerGesturesEnabled = true,
        drawerBackgroundColor = Color(0xAA000000),  //Transparent drawer for custom Drawer shape
        drawerElevation = 0.dp,
        drawerPeekHeight = 96.dp,

        content = {
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    Column {
                        SearchableTopBar(
                            modifier = Modifier
                                .padding(16.dp, 36.dp, 16.dp, 0.dp)
                                .background(Color.Transparent),

                            searchMode = searchMode ?: false,
                            searchText = searchText ?: "",
                            onSearchTextChanged = { viewModel.fetchSearchText(it) },
                            onSearchButtonClicked = { viewModel.fetchSearchModeTrue() }
                        )

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

@Composable
private fun DrawerContent(
    user: User?,
    serviceList: ServerResponseObj<Map<String, List<String>>>?,
    districtInfo: ServerResponse<District>?,
    recommendInfo: ServerResponse<Recommend>?,
    onServiceItemClick: (String) -> Unit,
    onDistrictButtonClick: (String) -> Unit,
    onDistrictItemClick: (Long) -> Unit
) {
    val placeHolderColor = Color(0xAA000000)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))

        Divider(modifier = Modifier
            .background(color = Color.White)
            .height(4.dp)
            .width(36.dp)
            .alpha(0.6f)
        )

        Spacer(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp))

        CreateListButton(serviceList, onServiceItemClick)

        Spacer(modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = user?.nickname ?: "null",
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
                    text = user?.nickname ?: "null",
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
    serviceList: ServerResponseObj<Map<String, List<String>>>?,
    onListButtonClick: (String) -> Unit,
) {
    val map = serviceList?.data
    val keys = map?.keys

    /*
    // 버튼 눌림시 효과 MutableInteractionSource
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val buttonBgColor = if (isPressed) Color.Black else Color(0xFF444548)
    */

    val buttonBgColor = Color(0xFF444548)

    var selectedButton by rememberSaveable { mutableStateOf("") }
    var isSubButtonShow by rememberSaveable { mutableStateOf(false) }
    var subButton by rememberSaveable { mutableStateOf(mutableListOf<String>()) }

    val onButtonSelected = { item: String ->
        Log.e("selectedButton", selectedButton)

        val setButtonState = {
            isSubButtonShow = true
            selectedButton = item
            subButton = map?.get(item)?.toMutableList() ?: mutableListOf()
        }

        if (selectedButton.isEmpty() or selectedButton.isBlank()) setButtonState()
        else when (selectedButton) {
            item -> isSubButtonShow = !isSubButtonShow
            else -> setButtonState()
        }
    }

    Column {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(keys?.toList() ?: listOf()) { _, item ->
                val size = if (item[0] in 'a'..'z') 12 else 24

                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = 8.dp,
                    modifier = Modifier
                        .height(48.dp)
                        .padding(4.dp),
                    backgroundColor = buttonBgColor
                ) {
                    TextButton(
                        modifier = Modifier
                            .width(size.dp * item.length),
                        onClick = { onButtonSelected(item) }
                    ) {
                        Text(text = item, color = Color.White)
                    }
                }
            }
        }

        if (isSubButtonShow) {
            LazyRow {
                itemsIndexed(subButton) { _, item ->
                    val size = if (item[0] in 'a'..'z') 12 else 24

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = 8.dp,
                        modifier = Modifier
                            .height(48.dp)
                            .padding(4.dp)
                    ) {
                        TextButton(
                            modifier = Modifier
                                .width(size.dp * item.length)
                                .background(buttonBgColor),
                            onClick = { onListButtonClick(item) }
                        ) {
                            Text(text = item, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchableTopBar(
    modifier: Modifier = Modifier,
    searchMode: Boolean,
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit
){
    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        backgroundColor = Color.White
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            visible = searchMode,
            enter = scaleIn() + expandHorizontally(),
            exit = scaleOut() + shrinkHorizontally()
        ) {
            BasicTextField(
                modifier = Modifier
                    .background(
                        Color(0xDDDDDDDD),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .height(36.dp),
                value = searchText,
                onValueChange = onSearchTextChanged,
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colors.primary),
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = MaterialTheme.typography.body2.fontSize
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f)) {
                            if (searchText.isEmpty()) Text(
                                text = "검색어를 입력하세요.",
                                style = LocalTextStyle.current.copy(
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
                                    fontSize = MaterialTheme.typography.body2.fontSize
                                )
                            )
                            innerTextField()
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                /* tint = LocalContentColor.current.copy(alpha = 0.5f) */
                            )
                        }
                    }
                }
            )
        }
    }

    if(!searchMode) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            TextButton(
                onClick = onSearchButtonClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    tint = Color.Black
                )
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

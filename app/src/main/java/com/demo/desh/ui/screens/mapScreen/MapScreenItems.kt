package com.demo.desh.ui.screens.mapScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import com.demo.desh.model.District
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.ServerResponseObj
import com.demo.desh.model.User
import com.demo.desh.ui.screens.CustomDivideLine
import com.demo.desh.ui.screens.CustomFullDivideLine
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

@Composable
fun DrawerContent(
    user: User?,
    serviceList: ServerResponseObj<Map<String, List<String>>>?,
    recommendInfo: ServerResponse<Recommend>?,
    districtInfo: ServerResponse<District>?,
    selectedServiceName: String?,
    defaultBackgroundColor: Color,
    onServiceItemClick: (String) -> Unit,
    onDistrictButtonClick: (String) -> Unit,
    onDistrictItemClick: (Long) -> Unit
) {
    /* Custom Colors */
    val phc = Color(0xAA000000)   // Place Holder Color
    val htc = Color(0xFFFF6A68)   // Highlight Text Color
    val dtc = Color.White               // Default Text Color
    val dbc = defaultBackgroundColor    // Default Button Color

    /* Custom Size */
    // Text
    val sts = 12.sp  // Small Text Size
    val dts = 16.sp  // Default Text Size
    val lts = 20.sp  // Large Text Size

    // Padding
    val sps = 8.dp   // Small Padding Size
    val dps = 16.dp  // Default Padding Size
    val lps = 32.dp  // Large Padding Size

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.padding(sps))

        CustomDivideLine()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dps),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Text(text = "조회 가능한 업종의 개수는 ", fontWeight = FontWeight.Bold, fontSize = dts, color = dtc)
                Text(text = "${serviceList?.size ?: 0}", fontWeight = FontWeight.Bold, fontSize = dts, color = htc)
                Text(text = "개 입니다.", fontWeight = FontWeight.Bold, fontSize = dts, color = dtc)
            }
        }

        CreateServiceListButton(
            serviceList = serviceList,
            buttonColor = dbc,
            onListButtonClick = onServiceItemClick
        )

        Spacer(Modifier.padding(dps))

        if (recommendInfo != null) {
            CustomFullDivideLine()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.padding(dps)) {
                    Text(
                        text = "\'${user?.nickname}\'",
                        color = htc,
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
                    textColor = dtc,
                    highlightTextColor = htc,
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
    }
}

@Composable
fun CreateServiceListButton(
    serviceList: ServerResponseObj<Map<String, List<String>>>?,
    buttonColor: Color,
    onListButtonClick: (String) -> Unit
) {
    val map = serviceList?.data
    val keys = map?.keys

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
            keys?.toList()?.let {
                itemsIndexed(it) { _, item ->
                    val size = if (item[0] in 'a'..'z') 12 else 24

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = 8.dp,
                        modifier = Modifier
                            .height(48.dp)
                            .padding(4.dp),
                        backgroundColor = buttonColor
                    ) {
                        TextButton(
                            modifier = Modifier
                                .width(size.dp * item.length),
                            onClick = { onButtonSelected(item) }
                        ) {
                            Text(text = item, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        if (isSubButtonShow) {
            LazyRow {
                itemsIndexed(subButton) { _, item ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        elevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(4.dp)
                    ) {
                        TextButton(
                            modifier = Modifier.background(buttonColor),
                            onClick = { onListButtonClick(item) }
                        ) {
                            Text(text = item, color = Color.White, fontSize = 12.sp)
                        }
                    }

                }
            }
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
            modifier = Modifier
                .clickable { onDistrictButtonClick(list[page].district) }
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


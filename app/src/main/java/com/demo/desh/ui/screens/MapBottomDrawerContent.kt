package com.demo.desh.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.model.District
import com.demo.desh.model.Recommend
import com.demo.desh.model.ServerResponse
import com.demo.desh.model.User
import com.demo.desh.ui.CustomFullDivideLine
import com.demo.desh.ui.theme.DefaultBackgroundColor
import com.demo.desh.ui.theme.HighlightColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager

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


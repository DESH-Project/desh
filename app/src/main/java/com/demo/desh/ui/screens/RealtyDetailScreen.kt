package com.demo.desh.ui.screens

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.demo.desh.model.BuildingInfo
import com.demo.desh.model.BuildingPreviewInfo
import com.demo.desh.model.DropdownItem
import com.demo.desh.model.buildingInfo
import com.demo.desh.model.buildingPreviewDummy
import com.demo.desh.ui.CommonScaffoldForm
import com.demo.desh.ui.CustomDropdownMenu
import com.demo.desh.ui.CustomIconMenu
import com.demo.desh.ui.UserProfileCard
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.ui.theme.Typography2
import com.demo.desh.viewModel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@Composable
fun RealtyDetailScreen(
    realtyId: Long,
    userViewModel: UserViewModel,
    goToProfileScreen: () -> Unit,
    goToChatListScreen: (Long) -> Unit
) {
    val user = userViewModel.user.value

    /* STATES */
    val open by userViewModel.open.observeAsState(initial = false)

    /* HANDLERS */

    CommonScaffoldForm(
        pbarOpen = open,
        topBarContent = {
            TopBarContent(
                goToProfileScreen = goToProfileScreen,
                goToChatListScreen = { goToChatListScreen(user?.id ?: 1L) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        ConstraintLayout {
            val (buildingImagePagerRef, buildingInfoUiRef, nearbyBuildingPreviewRef, remainsMarginRef) = createRefs()

            // 건물 이미지 Pager & Indicator
            BuildingImagePager(
                pageItems = buildingInfo.images,
                modifier = Modifier
                    .constrainAs(buildingImagePagerRef) {
                        top.linkTo(parent.top)
                        centerHorizontallyTo(parent)
                        width = Dimension.fillToConstraints
                    }
            )

            // 건물 상세 정보
            BuildingInfoUi(
                buildingInfo = buildingInfo,
                modifier = Modifier
                    .constrainAs(buildingInfoUiRef) {
                        top.linkTo(buildingImagePagerRef.bottom, margin = 16.dp)
                        linkTo(start = parent.start, end = parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            NearbyBuildingPreviewUi(
                nearbyBuildingInfo = buildingPreviewDummy,
                modifier = Modifier.constrainAs(nearbyBuildingPreviewRef) {
                    top.linkTo(anchor = buildingInfoUiRef.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
            )

            // 마지막 여백
            Spacer(modifier = Modifier.constrainAs(remainsMarginRef) {
                top.linkTo(anchor = nearbyBuildingPreviewRef.bottom, margin = 60.dp)
            })
        }
    }
}

@Composable
fun TopBarContent(
    goToProfileScreen: () -> Unit,
    goToChatListScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDropDownExpanded by remember { mutableStateOf(false) }
    val onDropdownExpand = { isDropDownExpanded = true }
    val onDropdownClose = { isDropDownExpanded = false }

    val dropdownItems = listOf(
        DropdownItem("공유하기", Color.Black),
        DropdownItem("신고하기", Color.Red)
    )

    Row(
        modifier = modifier.padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "GOODPLACE",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = Color.White
        )

        Row {
            CustomIconMenu(
                vector = Icons.Default.Email,
                onIconClick = goToChatListScreen
            )

            CustomIconMenu(
                vector = Icons.Default.AccountCircle,
                onIconClick = goToProfileScreen
            )

            CustomDropdownMenu(
                isDropDownExpanded = isDropDownExpanded,
                onDropdownExpand = onDropdownExpand,
                onDropdownClose = onDropdownClose,
                items = dropdownItems,
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BuildingImagePager(
    pageItems: List<String>,
    modifier: Modifier = Modifier
) {
    // TODO pageItems를 나중에 서버에서 받아온 값으로 변경하기
    val pagerState = rememberPagerState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .height(356.dp)
    ) {
        HorizontalPager(
            count = pageItems.size,
            state = pagerState,
            modifier = Modifier.align(Alignment.TopCenter)
        ) { page ->
            val item = pageItems[page]

            Card(
                backgroundColor = Color.White,
                shape = RoundedCornerShape(30.dp)
            ) {
                AsyncImage(
                    model = item,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color.White,
            inactiveColor = Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun BuildingInfoUi(
    buildingInfo: BuildingInfo,
    modifier: Modifier = Modifier
) {
    var starButtonState by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        // Line 1 (건물 이름, 찜하기 버튼)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = buildingInfo.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )

            IconButton(onClick = { starButtonState = !starButtonState }) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (starButtonState) Color.White else Color.Gray,
                )
            }
        }

        // Line 2


        // Line 3 (유저 프로필 카드, 문의하기 버튼)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            UserProfileCard(
                userNickname = buildingInfo.ownerNickname,
                userImage = buildingInfo.ownerProfileImage,
            )

            ChatButton()
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp), color = Color.Gray)

        // Line 4 (건물 정보)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            BuildingInfoMaker(imageVector = Icons.Default.LocationOn, text = buildingInfo.address)
            BuildingInfoMaker(imageVector = Icons.Default.Info, text = "3000 / 200")
            BuildingInfoMaker(imageVector = Icons.Default.Home, text = "${buildingInfo.pyung}평(${String.format("%.2f", buildingInfo.squareMeter)})")

            Text(
                text = "애뜨왈커피 로스팅 공장에서 운영하는 베이커리 카페로 젊은 감성있는 인테리어와 많은 주차 공간을 확보하고 있습니다..",
                style = Typography2.bodySmall,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Divider(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp), color = Color.Gray)
    }
}

@Composable
fun ChatButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(backgroundColor = HighlightColor, contentColor = Color.White),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = "문의하기", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun BuildingInfoMaker(
    imageVector: ImageVector? = null,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = imageVector!!,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.padding(start = 2.dp, end = 2.dp))
        
        Text(
            text = text,
            style = Typography2.bodySmall,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
    }
}

@Composable
fun NearbyBuildingPreviewUi(
    modifier: Modifier = Modifier,
    nearbyBuildingInfo: List<BuildingPreviewInfo>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        Text(
            text = "인근 상가 정보도 확인해보세요!",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = HighlightColor
        )

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        LazyRow {
            itemsIndexed(nearbyBuildingInfo) { _, item ->
                Card(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(shape = RoundedCornerShape(24.dp))
                ) {
                    Button(onClick = { /*TODO*/ }) {
                        AsyncImage(
                            model = item.previewImage,
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
            }
        }
    }
}
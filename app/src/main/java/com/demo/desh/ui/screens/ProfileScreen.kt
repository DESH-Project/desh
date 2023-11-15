package com.demo.desh.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.model.RealtyPreview
import com.demo.desh.ui.CommonScaffoldForm
import com.demo.desh.ui.TopBarContent
import com.demo.desh.ui.UserProfile
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.viewModel.ChatViewModel
import com.demo.desh.viewModel.UserViewModel


@Composable
fun ProfileScreen(
    userId: Long,
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel,
    goToProfileScreen: () -> Unit,
    goToChatListScreen: (Long) -> Unit
) {
    LaunchedEffect(Unit) {
        userViewModel.getUserInfo(userId)
        userViewModel.getUserRegStore(userId)
    }

    /* STATES */
    val user by userViewModel.targetUser.observeAsState()
    val open by userViewModel.open.observeAsState(initial = false)
    val userRegStore by userViewModel.userRegStore.observeAsState(initial = listOf())
    val userPickedStore by userViewModel.userPickedStore.observeAsState(initial = listOf())

    var btnSelected by rememberSaveable { mutableStateOf(true) }

    /* HANDLERS */
    val onPostBtnClick = {
        userViewModel.getUserPickedStore(userId)
        btnSelected = true
    }

    val onStarBtnClick = {
        userViewModel.getUserRegStore(userId)
        btnSelected = false
    }

    CommonScaffoldForm(
        pbarOpen = open,
        topBarContent = {
            TopBarContent(
                goToProfileScreen = goToProfileScreen,
                goToChatListScreen = { goToChatListScreen(userId) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        user?.let {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (userProfileRef, postAndLikeButtonRef, postAndLikeContentRef, remainsMarginRef) = createRefs()

                UserProfile(
                    userNickname = user!!.nickname,
                    profileImageUrl = user!!.profileImageUrl,
                    userDescription = user!!.description,
                    modifier = Modifier
                        .constrainAs(userProfileRef) {
                            centerHorizontallyTo(parent)
                            top.linkTo(anchor = parent.top, margin = 70.dp)
                        }
                )

                PostAndLikeButton(
                    onPostBtnClick = onPostBtnClick,
                    onStarBtnClick = onStarBtnClick,
                    btnSelected = btnSelected,
                    modifier = Modifier
                        .constrainAs(postAndLikeButtonRef) {
                            centerHorizontallyTo(parent)
                            top.linkTo(anchor = userProfileRef.bottom, margin = 24.dp)
                        },
                )

                PostAndLikeContent(
                    userRealtyPreview = if (btnSelected) userRegStore else userPickedStore,
                    modifier = Modifier
                        .constrainAs(postAndLikeContentRef) {
                            centerHorizontallyTo(parent)
                            top.linkTo(anchor = postAndLikeButtonRef.bottom, margin = 24.dp)
                        }
                )

                Spacer(modifier = Modifier.constrainAs(remainsMarginRef) {
                    top.linkTo(anchor = postAndLikeContentRef.bottom, margin = 60.dp)
                })
            }
        }
    }
}

@Composable
fun PostAndLikeContent(
    userRealtyPreview: List<RealtyPreview>,
    modifier: Modifier = Modifier
) {
    if (userRealtyPreview.isEmpty()) {
        Box(modifier.fillMaxSize()) {
            Text(
                text = "아직 표시할 콘텐츠가 없네요..",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }

    else {
        LazyVerticalGrid(
            userScrollEnabled = true,
            modifier = modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(15.dp),
            content = {
                items(userRealtyPreview) {
                    Card(modifier = Modifier
                        .size(128.dp)
                        .padding(10.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(model = it.previewImage, contentDescription = null)
                            Text(text = it.address)
                            Text(text = "${it.deposit} / ${it.monthlyRental}")
                            Text(text = it.star.toString())
                        }
                    }
                }
            }
        )
    }
}



@Composable
fun PostAndLikeButton(
    onPostBtnClick: () -> Unit,
    onStarBtnClick: () -> Unit,
    btnSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PreviewListButton(
            buttonText = "포스트",
            btnSelected = btnSelected,
            onClick = onPostBtnClick
        )

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))

        PreviewListButton(
            buttonText = "찜하기",
            btnSelected = !btnSelected,
            onClick = onStarBtnClick
        )
    }
}

@Composable
fun PreviewListButton(
    buttonText: String,
    btnSelected: Boolean,
    onClick: () -> Unit
) {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = if (btnSelected) HighlightColor else Color.DarkGray,
        contentColor = Color.White
    )

    Button(
        onClick = onClick,
        colors = buttonColors,
        modifier = Modifier
            .height(40.dp)
            .width(120.dp)
            .clip(shape = RoundedCornerShape(42.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
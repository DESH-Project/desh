package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.demo.desh.model.User
import com.demo.desh.ui.CommonScaffoldForm
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.viewModel.UserViewModel

val dummyImageList = listOf(
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/87114f2a-3c05-4c12-82d2-1d996f6a51d2.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/21e33cf3-b1dc-439d-af3a-0bd5d728a581.png",
    "https://goodplacebucket.s3.ap-northeast-2.amazonaws.com/d3dbe6e2-6513-44a7-a261-c04ff6328bd1.png",
)

@Composable
fun ProfileScreen(
    userId: Long? = null,
    userViewModel: UserViewModel
) {
    /* STATES */
    val user by userViewModel.user.observeAsState()

    CommonScaffoldForm(
        topBarContent = { /*TODO*/ }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (userProfileRef, postAndLikeButtonRef, postAndLikeContentRef) = createRefs()

            UserProfile(
                user = user,
                modifier = Modifier
                    .constrainAs(userProfileRef) {
                        centerHorizontallyTo(parent)
                        top.linkTo(anchor = parent.top, margin = 70.dp)
                    }
            )

            PostAndLikeButton(
                modifier = Modifier
                    .constrainAs(postAndLikeButtonRef) {
                        centerHorizontallyTo(parent)
                        top.linkTo(anchor = userProfileRef.bottom, margin = 36.dp)
                    },
            )

            PostAndLikeContent(
                previewImageList = dummyImageList,
                modifier = Modifier
                    .constrainAs(postAndLikeContentRef) {
                        centerHorizontallyTo(parent)
                        top.linkTo(anchor = postAndLikeButtonRef.bottom, margin = 24.dp)
                    }
            )
        }
    }
}

@Composable
fun PostAndLikeContent(
    previewImageList: List<String>,
    modifier: Modifier = Modifier
) {
   LazyVerticalGrid(
       modifier = modifier,
       columns = GridCells.Fixed(2),
       contentPadding = PaddingValues(10.dp),
       content = {
           items(previewImageList) {
               Card(
                   modifier = Modifier
                       .height(360.dp)
               ) {
                   AsyncImage(model = it, contentDescription = null)
               }
           }
       }
   )
}

@Composable
fun UserProfile(
    user: User?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = user?.profileImageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 12.dp)
                .clip(CircleShape)
                .size(120.dp)
        )

        /* TODO : 유저 소개글로 바꿔야함 */
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${user?.nickname}",
                fontSize = 24.sp,
                color = Color.White
            )

            Text(
                text = "고객의 니즈에 맞는 전문 맞춤 컨설팅으로",
                fontSize = 13.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 62.dp)
            )

            Text(
                text = "고객 만족까지 함께합니다.",
                fontSize = 13.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 90.dp)
            )
        }
    }
}

@Composable
fun PostAndLikeButton(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PreviewListButton(buttonText = "포스트", onClick = { })
        PreviewListButton(buttonText = "찜하기", onClick = { })
    }
}

@Composable
fun PreviewListButton(
    buttonText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = HighlightColor,
            contentColor = Color.White,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.LightGray
        ),
        modifier = Modifier
            .height(50.dp)
            .clip(shape = RoundedCornerShape(42.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buttonText,
                fontSize = 26.sp,
                color = Color.White
            )
        }
    }
}
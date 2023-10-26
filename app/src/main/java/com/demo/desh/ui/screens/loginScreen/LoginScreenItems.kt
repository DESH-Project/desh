package com.demo.desh.ui.screens.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.demo.desh.R
import com.demo.desh.model.LoginPreviewInfo

@Composable
fun SocialLoginButton(onKakaoLoginButtonClick: () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        IconButton(
            onClick = onKakaoLoginButtonClick,
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao_login_large_wide),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .width(345.dp)
                    .height(50.dp)
            )
        }

        IconButton(onClick = onKakaoLoginButtonClick) {
            Image(
                painter = painterResource(id = R.drawable.btng),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .width(345.dp)
                    .height(50.dp)
            )
        }
    }
}

@Composable
internal fun CustomAsyncImage(
    model: String,
    modifier: Modifier,
) {
    AsyncImage(
        model = model,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .then(modifier)
            .fillMaxHeight()
    )
}

@Composable
internal fun CustomArrowNextIcon(
    pageIndex: Int,
    modifier: Modifier,
    size: Int,
) {
    Icon(
        imageVector = Icons.Outlined.ArrowForward,
        contentDescription = null,
        tint = Color.White,
        modifier = modifier
            .padding(12.dp)
            .size(36.dp)
            .alpha(if (pageIndex == size - 1) 0f else 0.45f)
    )
}

@Composable
internal fun LoginIntroTextColumn(
    textData: List<LoginPreviewInfo>,
    modifier: Modifier,
    pageIndex: Int
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .padding(32.dp)
    ) {
        Text(
            text = textData[pageIndex].introduceText,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = textData[pageIndex].impactText,
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Text(
            text = textData[pageIndex].explainText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
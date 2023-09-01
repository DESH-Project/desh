package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.demo.desh.model.User
import com.demo.desh.viewModel.MainViewModel


@Composable
fun RealtyDetailScreen(
    realtyId: Long,
    user: User,
    viewModel: MainViewModel
) {
    val realtyDetail by viewModel.realtyDetail.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRealtyDetail(realtyId, user.id!!)
    }

    Scaffold { paddingValue ->
        Box(modifier = Modifier.padding(paddingValue)) {
            val info = realtyDetail?.list?.first()

            info?.apply {
                Column {
                    AsyncImage(model = image, contentDescription = "상가 이미지")
                    Text(text = "건물 PK : $id")
                    Text(text = "상가 이름 : $name")
                    Text(text = "가격 : $price")
                    Text(text = "주소 : $address")
                    Text(text = "평수 : $pyung")
                    Text(text = "제곱미터 : $squareMeter")
                    Text(text = "주변 상권 : $nearby")
                    Text(text = "조회한 유저 PK $userId")
                }
            }
        }
    }
}
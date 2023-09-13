package com.demo.desh.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.demo.desh.viewModel.MainViewModel

@Composable
fun RemainServiceListScreen(
    index: Int,
    viewModel: MainViewModel
) {
    val serviceList by viewModel.serviceList.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchServiceList()
    }



    LazyColumn {
        itemsIndexed(serviceList?.data?.slice(index until serviceList!!.size) ?: listOf()) { _, item ->
            Text(text = item)
        }
    }
}
package com.demo.desh.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.desh.api.RetrofitClient
import com.demo.desh.model.ServiceList
import com.demo.desh.util.MapCreator
import com.demo.desh.viewModel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun MapScreen(mainViewModel: MainViewModel = viewModel()) {
    val serviceList = getServiceList()
    val mv by mainViewModel.mapView.observeAsState()

    Column {
        CreateListButton(serviceList) { mainViewModel.updateMapView(it) }

        AndroidView(
            factory = if (mv == null) MapCreator.getCommonMapView() else mv!!,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

// ref: https://www.geeksforgeeks.org/horizontal-listview-in-android-using-jetpack-compose/
@Composable
private fun CreateListButton(
    serviceList: List<String>,
    onSelectedServiceNameChange: (String) -> Unit
) {
    LazyRow {
        itemsIndexed(serviceList) { _, item ->
            Card {
                TextButton(onClick = { onSelectedServiceNameChange(item) }) {
                    Text(text = item)
                }
            }
        }
    }
}

private fun getServiceList() : List<String> {
    val userService = RetrofitClient.userService
    val result = userService.getServiceList()
    val serviceList = mutableListOf<String>()

    result.enqueue(object : Callback<ServiceList> {
        override fun onResponse(call: Call<ServiceList>, response: Response<ServiceList>) {
            val body = response.body()!!
            val list = body.list
            list.forEach { serviceList.add(it) }
        }

        override fun onFailure(call: Call<ServiceList>, t: Throwable) {
        }
    })

    return serviceList
}
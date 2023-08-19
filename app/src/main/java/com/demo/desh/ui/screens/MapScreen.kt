package com.demo.desh.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.demo.desh.model.ServiceList
import com.demo.desh.util.MapViewCreator
import com.demo.desh.viewModel.MainViewModel


@Composable
fun MapScreen(viewModel: MainViewModel) {
    val serviceList by viewModel.serviceList.observeAsState()
    val mv by viewModel.mapView.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMapView()
        viewModel.fetchServiceList()
    }

    Log.e("MapScreen", "viewModel = $viewModel, serviceList = $serviceList, mapView = $mv")

    Column {
        CreateListButton(serviceList) { viewModel.fetchMapView(it) }

        AndroidView(
            factory = mv ?: MapViewCreator.createMapView(),
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun CreateListButton(
    serviceList: ServiceList?,
    onSelectedServiceNameChange: (String) -> Unit
) {
    LazyRow {
        itemsIndexed(serviceList?.list ?: listOf("Loading...")) { _, item ->
            Card {
                TextButton(onClick = { onSelectedServiceNameChange(item) }) {
                    Text(text = item)
                }
            }
        }
    }
}
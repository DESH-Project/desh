package com.demo.desh.ui.screens.mapScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlin.random.Random


/* vico guide */
// https://patrykandpatrick.com/vico/wiki/components
@Composable
fun MyChartParent() {
    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)

    val chartModifier = Modifier
        .fillMaxWidth()
        .background(Color.White)

    Chart(
        chart = lineChart(),
        model = chartEntryModel,
        modifier = chartModifier,
        startAxis = startAxis(),
        bottomAxis = bottomAxis()
    )

}

@Composable
fun MyChartParent2() {
    val chartEntryModelProducer = ChartEntryModelProducer(getRandomEntries())

    Chart(
        chart = columnChart(),
        chartModelProducer = chartEntryModelProducer,
        startAxis = startAxis(),
        bottomAxis = bottomAxis()
    )
}

fun getRandomEntries() = List(4) {
    entryOf(it, Random.nextFloat() * 16f)
}
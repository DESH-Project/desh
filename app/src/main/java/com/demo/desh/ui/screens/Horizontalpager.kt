package com.demo.desh.ui.screens
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.demo.desh.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@Composable
fun ViewOne(){
    Image(
        painter = painterResource(R.drawable.place),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.height(500.dp)
    )

}

@Composable
fun ViewTwo(){
    Image(
        painter = painterResource(R.drawable.place1),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .height(500.dp)
            .width(500.dp)
    )

}
@Composable
fun ViewThree(){
    Image(
        painter = painterResource(R.drawable.place2),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .height(500.dp)
            .width(500.dp)
    )

}
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun PagerContent(pagerState: PagerState) {

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {


            HorizontalPager(count = 3, state = pagerState) { page ->
                when (page) {
                    0 -> {
                        ViewOne()
                    }

                    1 -> {
                        ViewTwo()
                    }

                    2 -> {
                        ViewThree()
                    }

                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.BottomCenter),
                contentAlignment = Alignment.BottomCenter
            ) {
                HorizontalPagerIndicator(
                    pagerState = pagerState
                    , modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.BottomCenter).padding(top = 485.dp), activeColor = Color.Gray, inactiveColor = Color.White

                )
            }
        }
    }


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentView1(){
    val pagerState = rememberPagerState()
    Box() {

        PagerContent(pagerState = pagerState)


    }
}
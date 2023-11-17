package com.demo.desh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun RealtyAddScreen() {

}

@Composable
fun title(text1: String){
    Text(
        modifier = Modifier.padding(start = 30.dp, top = 30.dp),
        text = text1,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Color.White
    )
}
@Composable
fun previous() {
    Box(modifier =Modifier.padding(vertical = 1.dp)) {
        Box(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}
@Composable
fun bar(len:Int){
    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(top=1.dp)) {
            Divider(
                modifier = Modifier
                    .height((len * 2).dp)
                    .width(3.dp), color = Color(red = 247, green = 101, blue = 101)
            )
        }
    }
}
@Composable
fun t(text1:String){
    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, end = 28.dp)){
        Text(text = text1, color = Color.White)
    }
}

@Composable
fun building(navController: NavHostController) {
    bar(70)
    Column() {
        previous()
        title("건물이름을 정해주세요")
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Box(modifier = Modifier.padding(horizontal = 30.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                    .height(60.dp)
                    .width(350.dp)
            ) {
                buildingname()
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 250.dp))
        nextButton("Register")
    }
}

@Composable
fun buildingname(){
    Box {
        Box {
            var value3 by remember {
                mutableStateOf("")
            }
            Column {
                BasicTextField(
                    value = value3,
                    onValueChange = { newText ->
                        value3 = newText
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    ),
                    decorationBox = { innerTextField ->
                        Column {
                            Box(
                                modifier = Modifier
                                    .padding(start = 50.dp) // margin left and right
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp), // inner padding
                            ) {
                                if (value3.isEmpty()) {
                                    Text(
                                        text = "ex)시그니엘 아파트",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.LightGray
                                    )
                                }
                                innerTextField()
                            }
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun Register(navController: NavController) {
    bar(140)
    Column {
        previous()
        title("주소를 입력해주세요")
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Box(modifier = Modifier.padding(start=30.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                    .height(120.dp)
                    .width(350.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 5.dp)) {
                    adress()
                    Row {
                        detailadress()
                        Divider(
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = 1.dp)
                                .width(1.dp)

                        )
                        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                        detailadress2()
                    }

                }
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        radio()
        Spacer(modifier = Modifier.padding(vertical = 180.dp))
        nextButton("price")
    }
}

@Composable
fun adress() {
    Box {
        var value by remember {
            mutableStateOf("")
        }
        Column {
            BasicTextField(
                value = value,
                onValueChange = { newText ->
                    value = newText
                },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                ),
                decorationBox = { innerTextField ->
                    Column {
                        Box(
                            modifier = Modifier
                                .padding(start = 50.dp) // margin left and right
                                .fillMaxWidth()
                                .padding(vertical = 12.dp), // inner padding

                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "ex)강남구 논형동 1111",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.LightGray
                                )
                            }
                            innerTextField()
                        }
                        Box() {
                            Divider(
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(1.dp)
                                    .padding(horizontal = 50.dp)
                            )
                        }
                    }
                }
            )
        }
//            Spacer(modifier = Modifier.padding(vertical = 15.dp))
//            detailadress()
    }
}

@Composable
fun detailadress() {
    var value2 by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = value2,
        onValueChange = { newText ->
            value2 = newText
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        ),
        decorationBox = { innerTextField ->
            Column {
                Box(
                    modifier = Modifier
                        .padding(start = 50.dp) // margin left and right
                        .padding(vertical = 12.dp), // inner padding

                ) {
                    if (value2.isEmpty()) {
                        Text(
                            text = "동(선택)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }
//                    Box(modifier = Modifier.padding(horizontal = 50.dp)) {
//                        Divider(
//                            color = Color.White,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .width(1.dp)
//                        )

            }
        }
    )
}

@Composable
fun detailadress2() {
    var value3 by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = value3,
        onValueChange = { newText ->
            value3 = newText
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        ),
        decorationBox = { innerTextField ->
            Column {
                Box(
                    modifier = Modifier // margin left and right
                        .padding(vertical = 12.dp), // inner padding

                ) {
                    if (value3.isEmpty()) {
                        Text(
                            text = "호(선택)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.LightGray
                        )
                    }
                    innerTextField()
                }


            }
        }
    )
}

@Composable
fun radio() {
    var selectedValue by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.padding(start=26.dp)) {
        Row {
            RadioButton(
                selected = selectedValue,
                onClick = { selectedValue = !selectedValue }
            )
            Text(text = "단일동이거나 동이 없어요", modifier = Modifier.padding(vertical = 9.dp),color = Color.White)
        }
    }
}

@Composable
fun price(navController: NavController){
    bar(210)
    Column {
        previous()
        title(text1 = "보증금, 월세 선택해주세요")
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Box(modifier = Modifier.padding(start=30.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                    .height(120.dp)
                    .width(350.dp)
            ) {
                price1("보증금")
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Box(
            modifier = Modifier.padding(start=30.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                    .height(120.dp)
                    .width(350.dp)
            ) {
                price1("월세")
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 150.dp))
        nextButton("check")
    }
}

@Composable
fun price1(text1:String){
    Box {
        Box {
            var value by remember {
                mutableStateOf("")
            }
            Column {
                Text(text = text1,fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray, modifier = Modifier.padding(top = 22.dp,start = 42.dp ))
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                Divider(
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 36.dp)
                )
                BasicTextField(
                    value = value,
                    onValueChange = { newText ->
                        value = newText
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    ),
                    decorationBox = { innerTextField ->
                        Column {
                            Box(
                                modifier = Modifier
                                    .padding(start = 50.dp) // margin left and right
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp), // inner padding

                            ) {
                                if (value.isEmpty()) {
                                    Text(
                                        text = "0",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.LightGray
                                    )
                                }
                                Box(contentAlignment = androidx.compose.ui.Alignment.TopEnd, modifier = Modifier.fillMaxSize().padding(top = 3.dp,end=36.dp)){
                                    Text(text = "만원", color = Color.White)
                                }
                                innerTextField()
                            }
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun check(navController: NavController) {
    bar(280)
    Box() {
        Column {
            previous()
            title("주소를 확인해주세요")
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Box(modifier = Modifier.padding(start = 30.dp)) {
                Box(
                    modifier = Modifier
                        .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .height(100.dp)
                        .width(350.dp)
                ) {
                    addressname(text1 = "경기도 남양주시 진접읍 진벌로 389 미디어 뱅크턴 타워 더 퍼스트 제 10층 제 102-1032호[진벌로 82-3] ")
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Box(
                contentAlignment = Alignment.CenterEnd, modifier = Modifier
                    .padding(start = 274.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.background(
                        Color.DarkGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                    colors = ButtonDefaults.buttonColors(
                        Color.DarkGray
                    )
                ) {
                    Text(text = "주소수정", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Map()
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            nextButton("photo")
        }
    }
}


@Composable
fun addressname(text1:String){
    Text(text = text1, fontWeight = FontWeight.Normal, fontSize = 15.sp, color = Color.White,
        textAlign = TextAlign.Left, modifier = Modifier.padding(top=8.dp, start = 10.dp))
}

@Composable
fun Map() { //지도
    Box(modifier = Modifier.padding(start=30.dp)) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .width(350.dp)
        ) {
        }
    }
}

@Composable
fun photo(navController: NavController) {
    bar(350)
    Column {
        previous()
        title(text1 = "촬영한 매물 사진을 \n\n올려주세요")
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        phototext()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        buildingphoto()
        Spacer(modifier = Modifier.padding(vertical=10.dp))
        phototext2()
        Spacer(modifier = Modifier.padding(vertical=10.dp))
        buildingphoth2()
        Spacer(modifier = Modifier.padding(vertical=10.dp))
        nextButton("squaremeter")
    }
}
@Composable
fun phototext(){
    Column {
        Row(modifier = Modifier.padding(start = 30.dp)) {
            Text("매물사진", color = Color.White, fontStyle = FontStyle.Normal, fontSize = 18.sp)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                "(3장 이상)",
                color = Color(red = 245, green = 83, blue = 83),
                fontStyle = FontStyle.Normal,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        Text(text = "최대 20장까지 올릴 수 있어요",color = Color.Gray, fontStyle = FontStyle.Normal, fontSize = 18.sp, modifier = Modifier.padding(start = 30.dp))
    }
}
@Composable
fun buildingphoto() {
    Column {
        Box(modifier = Modifier.padding(start=30.dp)) {
            TextButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.DarkGray
                ),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                Text(text = "+ 매물사진 올리기")
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Row(modifier = Modifier.padding(start = 30.dp)) {
            Box(
            ) {
                Button(onClick = { /*TODO*/ }, modifier = Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
                    .height(100.dp)
                    .width(100.dp), colors = ButtonDefaults.buttonColors(
                    Color.DarkGray)) {
                }
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Box(
            ) {
                Button(onClick = { /*TODO*/ }, modifier = Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
                    .height(100.dp)
                    .width(100.dp), colors = ButtonDefaults.buttonColors(
                    Color.DarkGray)) {
                }
            }
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Box(
            ) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
                    .border(1.dp, Color.White, shape = RoundedCornerShape(5.dp))
                    .height(100.dp)
                    .width(100.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White)
                }
            }

        }
    }
}
@Composable
fun phototext2(){
    Column {
        Row(modifier = Modifier.padding(start = 30.dp)) {
            Text("평면도 사진", color = Color.White, fontStyle = FontStyle.Normal, fontSize = 18.sp)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                "(선택)",
                color = Color.Gray,
                fontStyle = FontStyle.Normal,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        Text(text = "최대 10장까지 올릴 수 있어요",color = Color.Gray, fontStyle = FontStyle.Normal, fontSize = 18.sp, modifier = Modifier.padding(start = 30.dp))
    }
}

@Composable
fun buildingphoth2(){
    Column {
        Box(modifier = Modifier.padding(start=30.dp)) {
            TextButton(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    Color.DarkGray
                ),
                modifier = Modifier
                    .width(350.dp)
                    .height(50.dp)
            ) {
                Text(text = "+평면도 사진 올리기")
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Row(modifier = Modifier.padding(start = 30.dp)) {
            Box(
            ) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(5.dp))
                    .border(1.dp, Color.White, shape = RoundedCornerShape(5.dp))
                    .height(100.dp)
                    .width(100.dp)) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.White)
                }
            }
        }
    }

}

@Composable
fun squaremeter(navController: NavController) {
    bar(420)
    Column {
        previous()
        title("평수를 입력해주세요")
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Box(modifier = Modifier.padding(start=30.dp)) {
            Box(
                modifier = Modifier
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                    .height(60.dp)
                    .width(350.dp)
            ) {
                meter()
                t("평")
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Map()
        Spacer(modifier = Modifier.padding(vertical=90.dp))
        nextButton(r = "building")

    }
}
@Composable
fun meter() {
    Box {
        Box {
            var value4 by remember {
                mutableStateOf("")
            }
            Column {
                BasicTextField(
                    value = value4,
                    onValueChange = { newText ->
                        value4 = newText
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    ),

                    decorationBox = { innerTextField ->
                        Column {
                            Box(
                                modifier = Modifier
                                    .padding(start = 50.dp) // margin left and right
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp), // inner padding
                            ) {
                                if (value4.isEmpty()) {
                                    Text(
                                        text = "0",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.LightGray
                                    )
                                }
                                innerTextField()
                            }
                        }

                    }
                )
            }
//                t("평")
        }
    }
}

@Composable
fun nextButton(r:String) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .padding(start = 30.dp)
    ) {
        Button(
            onClick = {navController.navigate(r)},
            modifier = Modifier
                .width(350.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(red = 247, green = 101, blue = 101)),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(red = 247, green = 101, blue = 101)
            )
        ) {
            Text(text = "다음")
        }
    }
}
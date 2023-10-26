import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.desh.R
import com.demo.desh.model.User
import com.demo.desh.ui.screens.ContentView1
import com.demo.desh.ui.theme.Typography2
import com.demo.desh.viewModel.UserViewModel

@Composable
fun RealtyDetailScreen(
    realtyId: Long,
    userViewModel: UserViewModel
) {
    val user by userViewModel.user.observeAsState()

    Surface(
        color = Color(0xFF343434),
        contentColor = Color.White
    ) {
        val scrollState = rememberScrollState();

        Column(Modifier.verticalScroll(scrollState)) {
            BuildingImage()
            Top()
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            profile1()
            Spacer(modifier = Modifier.padding(0.dp, 0.dp))
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(end = 20.dp, start = 20.dp)
            )
            buildingdetail()
            Spacer(modifier = Modifier.padding(0.dp, 7.dp))
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(end = 20.dp, start = 20.dp)
            )
            aroundbuilding()
            Spacer(modifier = Modifier.padding(0.dp, 10.dp))
            Text("주변상권", style = Typography2.bodyMedium, fontSize = 18.sp, modifier = Modifier.padding(vertical = 1.dp, horizontal = 19.dp))
            aroundstore("삼각지역(4호선, 6호선), 신용산역(4호선), 마포역(5호선),")
            aroundstore("숙대입구역(4호선), 효찰공원앞역(경의중앙선, 6호선),")
            aroundstore("공덕역(공항철도), 경의중앙선,5호선,6호선), 남양역(1호선),")
            aroundstore("용산역(1호선, 경의중앙선, KTX호남선, KTX전라선")
            Spacer(modifier = Modifier.padding(0.dp, 20.dp))
        }
    }
}



@Composable
fun Top() {
    Column(modifier = Modifier.padding(20.dp, 10.dp)) {
        var checked by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Row() {
            Text("오도르 카페", fontSize = 24.sp, style = Typography2.bodyLarge)
            Spacer(modifier = Modifier.padding(100.dp, 0.dp))

            IconToggleButton(
                checked = checked,
                onCheckedChange = { ischecked -> checked = ischecked },
                modifier = Modifier.padding(vertical = 9.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (checked) Color.White else Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            if(checked){
                Toast.makeText(context, "찜하기를 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.padding(1.dp))


        Row() {
            Card(
                backgroundColor = (Color.Black),
                modifier = Modifier
                    .height(40.dp)
                    .width(85.dp)

            )
            {
                Text(
                    "# 베이커리",
                    style = Typography2.bodySmall,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(5.dp, 0.dp))
            Card(
                backgroundColor = Color.Black , // 카드의 배경색을 파란색으로 설정
                modifier = Modifier
                    .height(40.dp)
                    .width(85.dp)
            ) {
                Text(
                    "# 카페",
                    style = Typography2.bodySmall,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun profile1() {
    Row() {
        IconButton(onClick = { }, modifier = Modifier.padding(start = 20.dp,)) {

            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(68.dp)

            )
        }
        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        Box() {
            names(names = "중개법인 김두식", day1 = "4일전")
        }
    }
}
@Composable
fun names(names: String, day1: String) {

    Text(
        names,
        fontSize = 15.sp,
        style = Typography2.bodyMedium,
    )
    Text(
        day1,
        fontSize = 12.sp,
        style = Typography2.bodyMedium, modifier = Modifier.padding(vertical = 30.dp)
    )

}
@Composable
fun BuildingImage() {
    var isDropDownExpanded by remember { mutableStateOf(false) }

    Surface {
        ContentView1()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopEnd)

        ) {
            IconButton(
                onClick = { isDropDownExpanded = true },
//                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).border(width = 7.dp, color =Color(0x20C7C1C1), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(35.dp)
                        .background(color = Color(0x20C7C1C1), shape = CircleShape))
            }
            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false },
                modifier = Modifier
                    .background(color = Color.Black)
            ) {
                DropdownMenuItem(onClick = { isDropDownExpanded = false }) {
                    Text(text = "공유하기", color = Color.White)

                }
                DropdownMenuItem(onClick = { isDropDownExpanded = false }) {
                    Text(text = "신고하기", color = Color.Red)

                }
            }
        }
    }
}

@Composable
fun buildingdetail() {
    Column(modifier = Modifier.padding(15.dp, 10.dp)) {
        Row(modifier = Modifier.padding(horizontal = 3.dp)) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(50.dp)
                    .padding(vertical = 12.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            Text(
                "경기 남양주시 진접음 경복대로",
                modifier = Modifier.padding(vertical = 1.dp),
                style = Typography2.bodySmall,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,

                )
        }

        Row(modifier = Modifier.padding(horizontal = 6.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.money), contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(46.dp)
                    .padding(vertical = 12.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            Text(
                "3000/200",
                modifier = Modifier.padding(1.dp),
                style = Typography2.bodySmall,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,

                )
        }
        Row(modifier = Modifier.padding(horizontal = 7.dp)) {
            Icon(
                painterResource(id = R.drawable.widthh),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(44.dp)
                    .padding(vertical = 12.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            Text(
                "78.15m",
                modifier = Modifier.padding(vertical = 1.dp),
                style = Typography2.bodySmall,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
        Row(modifier = Modifier.padding(horizontal = 3.dp)) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .size(50.dp)
                    .padding(vertical = 12.dp)
            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Text(
                "12.7",
                modifier = Modifier.padding(1.dp),
                style = Typography2.bodySmall,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

        Row() {
            Text(
                "애뜨왈커피 로스팅 공장에서 운영하는 베이커리 카페로 젊은 감성있는 인테리어와 많은 주차 공간을 확보하고 있습니다..",
                style = Typography2.bodySmall,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 40.dp))

        }
    }
}

@Composable
fun aroundbuilding() {
    Column {
        Text(
            "주변상가",
            style = Typography2.bodyMedium,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 19.dp)
        )

        val items = (1..50).toList()
        LazyRow() {
            items(items) { item ->
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 19.dp)
                        .size(130.dp)
                ) {
                    Text("")
                }
            }
        }
    }
}

@Composable
fun aroundstore(pharse: String) {
    Column() {

        Text(
            text = pharse,
            style = Typography2.bodySmall,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

    }
}
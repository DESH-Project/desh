import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.demo.desh.ui.theme.Typography2
import com.demo.desh.viewModel.MainViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun RealtyDetailScreen(
    realtyId: Long,
    user: User,
    viewModel: MainViewModel
) {
    Surface(color = Color(0xFF343434), contentColor = Color.White) {
        val scrollState = rememberScrollState();

        Column(Modifier.verticalScroll(scrollState)) {

            // A surface container using the 'background' color from the theme
            color()
            Top()
            Spacer(modifier = Modifier.padding(10.dp))
            profile()
            Spacer(modifier = Modifier.padding(0.dp, 10.dp))
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
            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(end = 20.dp, start = 20.dp)
            )
            Spacer(modifier = Modifier.padding(0.dp, 3.dp))
            Text("주변상가", style = Typography2.bodyMedium, fontSize = 20.sp, modifier = Modifier.padding(vertical = 5.dp, horizontal = 19.dp))
            aroundstore("올리브영")
            aroundstore("편의점")
            aroundstore("롯데리아")
            aroundstore("아마스빈")
            aroundstore("메가커피")


        }

    }
}
val customFontFamily = FontFamily(
    Font(R.font.notosanskr_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.notosanskr_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.notosanskr_extralight, FontWeight.Light, FontStyle.Normal),
    Font(R.font.notosanskr_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.notosanskr_semibold, FontWeight.SemiBold, FontStyle.Normal)
)

@Composable
fun Top() {
    Column(modifier = Modifier.padding(20.dp, 10.dp)) {
        var checked by remember { mutableStateOf(true) }

        Row() {
            Text("오도르 카페", fontSize = 24.sp, style = Typography2.bodyLarge)
            Spacer(modifier = Modifier.padding(100.dp, 0.dp))

            IconToggleButton(
                checked = checked,
                onCheckedChange = { ischecked -> checked = ischecked },
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (checked) Color.White else Color.Black

                )

            }
        }

        Spacer(modifier = Modifier.padding(1.dp))


        Row() {
            Card(

                modifier = Modifier
                    .height(40.dp)
                    .width(85.dp), shape = RoundedCornerShape(10.dp), backgroundColor = Color(
                    0x33000000
                )

            ) {
                Text(
                    "# 베이커리",
                    style = Typography2.bodySmall,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(5.dp, 0.dp))
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .width(85.dp), shape = RoundedCornerShape(10.dp), backgroundColor = Color(0x33000000)
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
fun profile(){
    Row(modifier = Modifier.padding(20.dp,1.dp)) {

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)

        )
        Column(modifier = Modifier.padding(5.dp,0.dp)){

            Text("중개법인 김두식", style = Typography2.bodyMedium)

        }

    }
}
@Composable
fun color(){
    Image(painter = painterResource(R.drawable.place), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = Modifier.height(500.dp))
}

@Composable
fun buildingdetail(){
    Column(modifier = Modifier.padding(15.dp,10.dp)) {
        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
            Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("경기 남양주시 진접음 경복대로", style = Typography2.bodySmall, fontSize = 14.sp, textAlign = TextAlign.Center)
        }

        Row(modifier = Modifier.padding(horizontal = 10.dp)){
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = null,tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("3000/200", style = Typography2.bodySmall, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
        Row(modifier = Modifier.padding(horizontal = 10.dp)){
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null,tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("78.15m", style = Typography2.bodySmall, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
        Row(modifier = Modifier.padding(horizontal = 10.dp)){
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null,tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("12.7", style = Typography2.bodySmall, fontSize = 14.sp, textAlign = TextAlign.Center)
        }

        Text("애뜨왈커피 로스팅 공장에서 운영하는 베이커리 카페로 젊은 감성있는 인테리어와 많은 주차 공간을 확보하고 있습니다.", style = Typography2.bodySmall, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 40.dp))
    }
}
@Composable
fun aroundbuilding() {
    Column {
        Text("주변상권", style = Typography2.bodyMedium, fontSize = 20.sp, modifier = Modifier.padding(vertical = 5.dp, horizontal = 19.dp))

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
fun aroundstore(pharse: String){
    Column() {
        Card(
            backgroundColor = Color.Gray,
            modifier = Modifier.padding(horizontal = 19.dp)
            .height(40.dp)
            .width(380.dp), shape = RoundedCornerShape(5.dp)

        ) {
        Text(text = pharse,style = Typography2.bodySmall,fontSize = 15.sp, textAlign = TextAlign.Center)
    }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))

  }
}

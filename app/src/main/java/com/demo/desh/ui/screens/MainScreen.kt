import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.desh.R
import com.demo.desh.viewModel.MainViewModel

@Composable
fun RealtyDetailScreen(
    viewModel: MainViewModel,
    realtyId: Long
) {
    val scrollState = rememberScrollState();

    Column(Modifier.verticalScroll(scrollState)) {

        // A surface container using the 'background' color from the theme
        color()
        Top()
        Spacer(modifier = Modifier.padding(10.dp))
        profile()
        Spacer(modifier = Modifier.padding(0.dp,10.dp))
        Divider(color = Color.White , thickness = 1.dp, modifier = Modifier.padding(end = 20.dp, start = 20.dp))
        buildingdetail()
        Spacer(modifier = Modifier.padding(0.dp,7.dp))
        Divider(color = Color.White , thickness = 1.dp, modifier = Modifier.padding(end = 20.dp, start = 20.dp))
        aroundbuilding()

    }

}

@Composable
fun Top() {
    Column(modifier = Modifier.padding(20.dp,10.dp)) {
        var checked by remember { mutableStateOf(true) }

        Row() {
            Text("오도르 카페")
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
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.buttoncolor))
            ) {
                Text("베이커리", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.padding(5.dp, 0.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.buttoncolor))
            ) {
                Text("카페", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun profile(){
    Row(modifier = Modifier.padding(20.dp,1.dp)) {

        Image(
            painter = painterResource(id = R.drawable.chamomile),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)

        )
        Column(modifier = Modifier.padding(5.dp,0.dp)){

            Text("중개법인 김두식")

        }

    }
}
@Composable
fun color(){
    Image(painter = painterResource(R.drawable.chamomile), contentDescription = null, contentScale = ContentScale.FillWidth, modifier = Modifier.height(500.dp))
}

@Composable
fun buildingdetail(){
    Column(modifier = Modifier.padding(15.dp,10.dp)) {
        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
            Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("경기 남양주시 진접음 경복대로")
        }

        Row(modifier = Modifier.padding(horizontal = 10.dp)){
            Icon(imageVector = Icons.Default.AddCircle, contentDescription = null,tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("3000/200")
        }
        Row(modifier = Modifier.padding(horizontal = 10.dp)){
            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null,tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("78.15m")
        }
        Row(modifier = Modifier.padding(horizontal = 10.dp)){
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null,tint = Color.Gray, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text("12.7")
        }

        Text("애뜨왈커피 로스팅 공장에서 운영하는 베이커리 카페로 젊은 감성있는 인테리어와 많은 주차 공간을 확보하고 있습니다.")
    }
}
@Composable
fun aroundbuilding() {
    Column {
        Text("주변상가", fontSize = 20.sp, modifier = Modifier.padding(vertical = 5.dp, horizontal = 19.dp))

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
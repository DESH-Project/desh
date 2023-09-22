import android.widget.Toast
import androidx.annotation.ContentView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.demo.desh.R
import com.demo.desh.model.User
import com.demo.desh.ui.screens.ContentView1
import com.demo.desh.ui.theme.Typography2
import com.demo.desh.viewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun RealtyDetailScreen(
    realtyId: Long,
    user: User,
    viewModel: MainViewModel
) {
    Surface(color = Color(0xFF343434), contentColor = Color.White) {
        val navController = rememberNavController()
        val scrollState = rememberScrollState();

        Column(Modifier.verticalScroll(scrollState)) {

            // A surface container using the 'background' color from the theme
            BuildingImage()
            Top()
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            profile1(navController)
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
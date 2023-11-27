package com.demo.desh.ui.screens

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.demo.desh.model.RealtyCreationReq
import com.demo.desh.ui.theme.HighlightColor
import com.demo.desh.viewModel.UserViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

@SuppressLint("Range")
fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
    return contentResolver.query(this, null, null, null, null)?.let {
        if (it.moveToNext()) {
            val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            val requestBody = object : RequestBody() {
                override fun contentType(): MediaType? {
                    return contentResolver.getType(this@asMultipart)?.toMediaType()
                }

                override fun writeTo(sink: BufferedSink) {
                    sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                }
            }
            it.close()
            MultipartBody.Part.createFormData(name, displayName, requestBody)
        } else {
            it.close()
            null
        }
    }
}

@Composable
fun RealtyAddScreen(
    userViewModel: UserViewModel,
    goToHomeScreen: () -> Unit,
    goToProfileScreen: (Long) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userViewModel.getUserInfo(1L)
    }

    val user by userViewModel.targetUser.observeAsState()
    Log.e("RealtyAddScreen", user.toString())

    var pageState by rememberSaveable { mutableStateOf(1) }
    val onPageNext = { pageState += 1 }

    var address by rememberSaveable { mutableStateOf("") }
    val onAddressChanged = { text: String -> address = text }

    var deposit by rememberSaveable { mutableStateOf("0") }
    var monthly by rememberSaveable { mutableStateOf("0") }
    val onDepositChanged = { text: String -> deposit = text }
    val onMonthlyChanged = { text: String -> monthly = text }

    var selectedImages by rememberSaveable { mutableStateOf(listOf<Uri>()) }
    val onImageSelected = { uris: List<@JvmSuppressWildcards Uri> -> selectedImages = uris }

    val onUploadInfo = {
        val req = RealtyCreationReq(
            name = "test",
            address = address,
            deposit = deposit.toInt(),
            monthlyRental = monthly.toInt(),
            pyung = 36,
            squareMeter = (36 * 3.3).toDouble(),
            service = "Test",
            userId = user?.userId ?: 3L
        )

        Log.e("onUploadInfo", req.toString())

        val parts = selectedImages.mapNotNull { it.asMultipart("images", context.contentResolver) }

        runBlocking {
            userViewModel.uploadRealtyInfo(images = parts, req = req)
        }

        Toast.makeText(context, "등록이 완료되었습니다!", Toast.LENGTH_SHORT).show()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        when (pageState) {
            1 -> InputAddress(address, onAddressChanged, onPageNext)
            2 -> InputRentalFee(deposit, onDepositChanged, monthly, onMonthlyChanged, onPageNext)
            3 -> InputStorePics(selectedImages, onImageSelected, onUploadInfo)
        }
    }
}

@Composable
fun TitleMaker(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 35.sp,
        color = Color.White,
        modifier = Modifier
            .padding(start = 30.dp, top = 30.dp)
    )
}

@Composable
fun CustomTextField(
    placeHolderText: String,
    labelText: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        placeholder = { Text(text = placeHolderText, color = Color.LightGray) },
        label = { Text(text = labelText) },
        value = text,
        onValueChange = { onTextChange(it) },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        shape = RoundedCornerShape(25.dp)
    )
}

@Composable
fun CustomNextButton(onPageNext: () -> Unit) {
    Button(
        onClick = onPageNext,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 25.dp)
    ) {
        Text(text = "다음")
    }
}

@Composable
fun InputAddress(
    address: String,
    onAddressChanged: (String) -> Unit,
    onPageNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TitleMaker("주소를 입력해주세요")

        CustomTextField(
            placeHolderText = "강남구 논현동 1111",
            labelText = "Address",
            text = address,
            onTextChange = onAddressChanged
        )

        Spacer(modifier = Modifier.padding(30.dp))

        CustomNextButton(onPageNext)
    }
}

@Composable
fun InputRentalFee(
    deposit: String,
    onDepositChanged: (String) -> Unit,
    monthly: String,
    onMonthlyChanged: (String) -> Unit,
    onPageNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TitleMaker("보증금, 월세를 입력해주세요")

        CustomTextField(
            placeHolderText = "보증금",
            labelText = "Deposit",
            text = deposit,
            onTextChange = onDepositChanged
        )

        Spacer(modifier = Modifier.padding(10.dp))

        CustomTextField(
            placeHolderText = "월세",
            labelText = "Monthly",
            text = monthly,
            onTextChange = onMonthlyChanged
        )

        Spacer(modifier = Modifier.padding(30.dp))

        CustomNextButton(onPageNext)
    }
}

@Composable
fun InputStorePics(
    selectedImageUris: List<Uri>,
    setSelectedImageUris: (List<Uri>) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> setSelectedImageUris(uris) }
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TitleMaker("직접 촬영한 매물 사진을 올려주세요")

        Spacer(modifier = Modifier.padding(10.dp))

        Card(
            shape = RoundedCornerShape(25.dp),
            backgroundColor = Color.DarkGray,
            elevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 25.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tip",
                        color = HighlightColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = "다양한 각도의 자세한 사진이 있으면 연락 올 확률이 높아져요!",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Column(
            modifier = Modifier.padding(start = 25.dp)
        ) {
            Row {
                Text("매물 사진 ", color = Color.White, fontSize = 18.sp)
                Text("(3장 이상)", color = HighlightColor, fontSize = 18.sp)
            }

            Text(text = "최대 20장까지 올릴 수 있어요", color = Color.Gray)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Card(
            shape = RoundedCornerShape(25.dp),
            backgroundColor = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 25.dp)
                .clickable {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "+ 매물 사진 올리기",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.padding(15.dp))
        
        CustomNextButton(onSubmit)
    }
}

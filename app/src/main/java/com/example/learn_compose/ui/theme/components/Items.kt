package com.example.learn_compose.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.learn_compose.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemMeeting(
    list: List<String>,
    image: String,
    date: String,
    sity: String,
    title: String,
    onClick: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke("r3r") },
    ) {
        Row(
            Modifier
                .padding(top = 16.dp)
                .height(66.dp)
                .fillMaxWidth(),
        ) {
            GlideImage(
                model = image,
                contentDescription = "",
                modifier =
                    Modifier
                        .size(56.dp)
                        .padding(4.dp),
                failure = placeholder(R.drawable.icon__2_),
                loading = placeholder(R.drawable.icon_meeting),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.fillMaxWidth()) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight(600))
                Spacer(modifier = Modifier.height(2.dp))
                Row {
                    Text(
                        text = date,
                        color = Color(0xFFA4A4A4),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                    )
                    Text(
                        text = "-",
                        color = Color(0xFFA4A4A4),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                    )
                    Text(
                        text = sity,
                        color = Color(0xFFA4A4A4),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                TagsBut(list)
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Divider()
    }
}

@Composable
fun TagsBut(list: List<String>) {
    Row {
        list.forEachIndexed { _, text ->
            androidx.compose.material3.Card(
                modifier =
                    Modifier
                        .padding(end = 4.dp)
                        .width(48.dp)
                        .height(20.dp),
                colors = CardDefaults.cardColors(contentColor = Color(0xFF660EC8)),
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "$text",
                        style =
                            androidx.compose.ui.text
                                .TextStyle(fontWeight = FontWeight(600)),
                        color = Color(0xFF660EC8),
                        fontSize = 10.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCommunity(
    title: String,
    bottomTitle: String,
    image: Int,
) {
    Column {
        Row(
            Modifier
                .height(68.dp)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(40))
                        .padding(4.dp)
                        .size(56.dp),
                contentScale = ContentScale.Crop,
            )
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight(600))
                Text(
                    text = bottomTitle,
                    color = Color(0xFFA4A4A4),
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400),
                    modifier = Modifier.padding(top = 25.dp),
                )
            }
        }
        Divider()
    }
}

@Composable
fun ProfileCard(
    onClick: () -> Unit = {},
    image: Int = R.drawable.avatar,
    name: String = "Иванов Иван",
    number: String = "+7 999 999-99-99",
) {
    Row(
        Modifier
            .height(66.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick.invoke() },
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier =
                Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically),
        )
        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp, horizontal = 20.dp),
        ) {
            Text(text = name, Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight(600)))
            Text(
                text = number,
                color = Color(0xFFADB5BD),
                style = TextStyle(fontWeight = FontWeight(400)),
            )
        }
        androidx.compose.material.Icon(
            painter = painterResource(id = R.drawable.icon_back_),
            contentDescription = "",
            modifier =
                Modifier
                    .align(Alignment.CenterVertically)
                    .graphicsLayer(rotationZ = 180f),
        )
    }
}

@Composable
fun ItemSetting(
    iconStart: Int = R.drawable.icon_meeting,
    iconEnd: Int = R.drawable.icon_back_,
    nameSetting: String = "Мои встречи",
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth()
                .height(40.dp)
                .clickable { onClick.invoke() },
    ) {
        androidx.compose.material.Icon(
            painter = painterResource(id = iconStart),
            contentDescription = "",
            modifier =
                Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = nameSetting,
            modifier =
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
            style = TextStyle(fontWeight = FontWeight(600), fontSize = 14.sp),
        )
        androidx.compose.material.Icon(
            painter = painterResource(id = iconEnd),
            contentDescription = "",
            modifier =
                Modifier
                    .graphicsLayer(rotationZ = 180f)
                    .align(Alignment.CenterVertically),
        )
    }
}
@Composable
fun ButtonItem(modifier: Modifier,buttonList: List<ButtonList> = listOf(ButtonList(R.drawable.icon_instagram, ""))) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        buttonList.forEach {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = CircleShape ,
                border = BorderStroke(1.4.dp, color = Color(0xFF9A41FE)),
                modifier = Modifier
                    .size(72.dp, 40.dp)
                    .clickable { },
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Image(
                        painter = painterResource(id = it.image),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        }
    }
}

data class ButtonList(
    val image:Int,
    val title:String
)
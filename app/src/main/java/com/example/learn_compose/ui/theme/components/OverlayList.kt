package com.example.learn_compose.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex

@Composable
fun ImageOverlayList(
    images: List<Int>,
    additionalCount: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp),
    ) {
        images.take(5).forEachIndexed { index, image ->
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier =
                    Modifier
                        .offset(x = ((images.size - index) - 6) * 10.dp, 0.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color(0xff166FF6), RoundedCornerShape(12.dp))
                        .zIndex(-index.toFloat()),
                contentScale = ContentScale.FillBounds,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "+$additionalCount",
            modifier =
                Modifier
                    .offset(x = -images.size * 5.dp, y = 1.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}

package com.example.learn_compose.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonBase(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    loading: Boolean = false,
    secondary: Boolean = false,
) {
    val gradientBrush =
        Brush.horizontalGradient(
            colors =
                listOf(
                    Color(0xFFED3CCA),
                    Color(0xFF6600FF),
                ),
        )
    val gradientBrushSecondary =
        Brush.horizontalGradient(
            colors =
                listOf(
                    Color(0xFFFEF1FB),
                    Color(0xFFF4EDFF),
                ),
        )

    val buttonColors =
        when {
            loading ->
                ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White,
                )

            !enabled ->
                ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFFF6F6FA),
                    contentColor = Color(0xFF9797AF),
                )

            secondary ->
                ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF9A10F0),
                )

            else ->
                ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                )
        }

    Box(
        modifier =
            modifier
                .background(
                    brush =
                        if (enabled && !secondary) {
                            gradientBrush
                        } else if (secondary) {
                            gradientBrushSecondary
                        } else {
                            SolidColor(Color(0xFFF6F6FA))
                        },
                    shape = RoundedCornerShape(8.dp),
                ).clip(RoundedCornerShape(10.dp)),
    ) {
        OutlinedButton(
            onClick = { if (enabled && !loading) onClick() },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled && !loading,
            colors = buttonColors,
            contentPadding = PaddingValues(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            border = null,
        ) {
            when {
                loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp,
                    )
                }

                else -> {
                    Text(text = text, fontSize = 18.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    ButtonBase(text = "Оплатить", modifier = Modifier, secondary = true)
}

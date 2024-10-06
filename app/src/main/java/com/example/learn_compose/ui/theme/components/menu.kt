package com.example.learn_compose.ui.theme.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learn_compose.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryMenu(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val codeCountry = listOf("+7", "+375", "+996", "+892")
    val imageRes = listOf(
        R.drawable.rus_flag,
        R.drawable.bel_flag,
        R.drawable.krgz_flag,
        R.drawable.azer_flag
    )

    var selectedOptionText by remember { mutableStateOf(codeCountry[0]) }
    var selectedImage by remember { mutableStateOf(imageRes[0]) }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        Row(modifier = modifier) {
            Icon(
                painterResource(id = selectedImage),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = modifier.padding(end = 8.dp)
            )
            Text(
                text = selectedOptionText,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                fontSize = 14.sp
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            codeCountry.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        selectedImage = imageRes[index] // Update the selected image based on index
                        expanded = false
                        onClick(selectionOption)
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painterResource(id = imageRes[index]), contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewCountryMenu() {
    CountryMenu(onClick = {})
}
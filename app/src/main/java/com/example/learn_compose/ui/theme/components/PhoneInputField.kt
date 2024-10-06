package com.example.learn_compose.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.learn_compose.ui.theme.Typography

@Composable
fun PhoneInputField(
    onValueChange: (Any?) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = Typography.bodyLarge.fontSize,
    mask: String = "000 000 00 00",
    maskNumber: Char = '0',
) {
    var phoneNumber by remember { mutableStateOf("") }

    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        BasicTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier =
                modifier
                    .background(Color(0xFFADB5BD))
                    .fillMaxWidth(),
            value = phoneNumber,
            onValueChange = { newValue ->
                if (newValue.length <= mask.count { it == maskNumber }) {
                    phoneNumber = newValue
                    onValueChange(newValue)
                }
            },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.errorContainer),
            textStyle =
                LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = fontSize,
                ),
            visualTransformation = PhoneVisualTransformation(mask, maskNumber),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (leadingIcon != null) leadingIcon()

                    Box(Modifier.weight(1f)) {
                        if (phoneNumber.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style =
                                    LocalTextStyle.current.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                        fontSize = fontSize,
                                    ),
                            )
                        }
                        innerTextField()
                    }
                }
            },
        )
    }
}

class PhoneVisualTransformation(
    val mask: String,
    val maskNumber: Char,
) : VisualTransformation {
    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString =
            buildAnnotatedString {
                if (trimmed.isEmpty()) return@buildAnnotatedString

                var maskIndex = 0
                var textIndex = 0
                while (textIndex < trimmed.length && maskIndex < mask.length) {
                    if (mask[maskIndex] != maskNumber) {
                        val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                        append(mask.substring(maskIndex, nextDigitIndex))
                        maskIndex = nextDigitIndex
                    }
                    append(trimmed[textIndex++])
                    maskIndex++
                }
            }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun hashCode(): Int = mask.hashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneVisualTransformation

        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        if (maxLength != other.maxLength) return false

        return true
    }

    private class PhoneOffsetMapper(
        val mask: String,
        val numberChar: Char,
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (mask.isEmpty()) return offset

            var noneDigitCount = 0
            var i = 0
            while (i < offset + noneDigitCount && i < mask.length) {
                if (mask[i] != numberChar) noneDigitCount++
                i++
            }
            return minOf(offset + noneDigitCount, mask.length)
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (mask.isEmpty()) return offset

            return offset - mask.take(offset).count { it != numberChar }
        }
    }
}

@Composable
fun CharView(
    index: Int,
    text: String,
) {
    val char =
        when {
            index == text.length -> ""
            index > text.length -> ""
            else -> text[index].toString()
        }
    Text(
        modifier =
            Modifier
                .width(42.dp)
                .clip(CircleShape)
                .background(if (char.isNotEmpty()) Color.Transparent else Color(0xFFEDEDED)) //
                .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.Black,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun NameFamilyTextField(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "",
    textStyle: TextStyle = TextStyle(),
    spaseBetweenImageAndText: Dp = 0.dp,
) {
    var textValue by remember { mutableStateOf("") }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        BasicTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier =
                modifier
                    .background(Color(0xFFF7F7FC))
                    .fillMaxWidth(),
            value = textValue,
            onValueChange = { newValue ->
                textValue = newValue
                onValueChange(newValue)
            },
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.errorContainer),
            textStyle =
                TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = textStyle.fontSize,
                ),
            decorationBox = { innerTextField ->
                Row(
                    modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Spacer(modifier = Modifier.width(spaseBetweenImageAndText))
                    Box(Modifier.weight(1f)) {
                        if (textValue.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style =
                                    LocalTextStyle.current.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                        fontSize = textStyle.fontSize,
                                    ),
                            )
                        }
                        innerTextField()
                    }

                    if (trailingIcon != null) trailingIcon()
                }
            },
        )
    }
}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int,
    onOtpTextChange: (String, Boolean) -> Unit,
) {
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(index = index, text = otpText)
                    Spacer(modifier = Modifier.width(40.dp))
                }
            }
        },
    )
}

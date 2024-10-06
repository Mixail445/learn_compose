package com.example.learn_compose.utils

fun formatNumberWithCodeCountry(code: String, number: String): String {
    val cleanedNumber = number.replace(Regex("[^\\d]"), "")

    val (mask, _) = getMaskForCountry(code)

    return applyMaskToNumber(mask, cleanedNumber)
}

private fun applyMaskToNumber(mask: String, number: String): String {
    var formattedNumber = ""
    var numberIndex = 0

    for (char in mask) {
        if (char == '0') {
            if (numberIndex < number.length) {
                formattedNumber += number[numberIndex]
                numberIndex++
            } else {
                break
            }
        } else {
            formattedNumber += char
        }
    }

    return formattedNumber
}
fun getMaskForCountry(countryCode: String): Pair<String, String> {
    return when (countryCode) {
        "+7" -> Pair("000 000-00-00", "000 000-00-00")
        "+375" -> Pair("00 000 00 00", "00 000 00 00")
        "+996" -> Pair("0 000 000-00-00", "0 000 000-00-00")
        "+892" -> Pair("0 000 00 00", "0 000 00 00")
        else -> Pair("", "")
    }
}
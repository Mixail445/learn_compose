package com.example.learn_compose.domain

import com.example.learn_compose.data.ResponseMessageDto
import com.example.learn_compose.data.UserPhoneNumberDto
import com.example.learn_compose.data.UserProfileDto
import com.example.learn_compose.data.VerificationCodeDto

data class Meetings(
    val language: String,
    val level: String,
    val city: String,
    val date: String,
    val active: Boolean = false,
    val image: String,
    val listTag: List<String>,
)

data class ResponseMessage(
    val message: String,
) {
    fun mapToDto() = ResponseMessageDto(message)
}

data class VerificationCodeModel(
    val code: String,
) {
    fun mapToDto() = VerificationCodeDto(code)
}

data class UserProfile(
    val firstName: String,
    val lastName: String,
) {
    fun mapToDto() = UserProfileDto(firstName, lastName)
}

data class UserPhoneDomain(
    val phoneNumber: String,
) {
    fun mapToDto() = UserPhoneNumberDto(phoneNumber)
}

data class UserPhoneNumber(
    val number: String,
) {
    fun mapFromDto() = UserPhoneNumberDto(number)
}

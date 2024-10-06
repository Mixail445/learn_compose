package com.example.learn_compose.data

import com.example.learn_compose.domain.ResponseMessage
import com.example.learn_compose.domain.UserPhoneDomain
import com.example.learn_compose.domain.UserProfile
import com.example.learn_compose.service.VerificationCode

data class MeetingsDto(
    val language: String,
    val level: String,
    val city: String,
    val date: String,
    val active: Boolean = false,
    val image: String,
    val list: List<String>,
)

data class VerificationCodeDto(
    val code: String,
)

data class UserProfileDto(
    val firstName: String,
    val lastName: String,
)

data class ResponseMessageDto(
    val message: String,
)

data class UserPhoneNumberDto(
    val phoneNumber: String,
)


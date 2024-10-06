package com.example.learn_compose.data

import com.example.learn_compose.domain.Meetings
import com.example.learn_compose.domain.ResponseMessage
import com.example.learn_compose.domain.UserPhoneDomain
import com.example.learn_compose.domain.UserProfile
import com.example.learn_compose.service.VerificationCode

fun MeetingsDto.mapToDomain() =
    Meetings(
        level = level,
        language = language,
        date = date,
        active = active,
        image = image,
        listTag = list,
        city = city,
    )
fun VerificationCodeDto.mapToDomain() = VerificationCode(code)

fun UserProfileDto.mapToDomain() = UserProfile(firstName, lastName)

fun ResponseMessageDto.mapToDomain() = ResponseMessage(message)

fun UserPhoneNumberDto.mapToDomain() = UserPhoneDomain(phoneNumber = phoneNumber)
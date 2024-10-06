package com.example.learn_compose.domain

import com.example.learn_compose.data.MeetingsDto
import com.example.learn_compose.data.ResponseMessageDto
import com.example.learn_compose.data.UserPhoneNumberDto
import com.example.learn_compose.data.UserProfileDto
import com.example.learn_compose.data.VerificationCodeDto

interface RemoteSource {
    suspend fun getMeetings(): List<MeetingsDto>

    suspend fun getTest(url: String): List<MeetingsDto>

    suspend fun sendCode(userPhoneNumber: UserPhoneNumberDto): ResponseMessageDto

    suspend fun verifyCode(verificationCode: VerificationCodeDto): Boolean

    suspend fun submitProfile(
        token: String,
        userProfile: UserProfileDto,
    ): Boolean

    suspend fun getAllUsers(token: String): List<UserProfileDto>
}



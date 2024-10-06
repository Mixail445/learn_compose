package com.example.learn_compose.domain

import com.example.learn_compose.utils.AppResult

interface Repository {
    suspend fun getTest(url: String): AppResult<List<Meetings>, Throwable>

    suspend fun getMeetings(): AppResult<List<Meetings>, Throwable>

    suspend fun sendCode(userPhoneNumber: UserPhoneNumber): AppResult<ResponseMessage, Throwable>

    suspend fun verifyCode(verificationCode: VerificationCodeModel): AppResult<Boolean, Throwable>

    suspend fun submitProfile(
        token: String,
        userProfile: UserProfile,
    ): AppResult<Boolean, Throwable>

    suspend fun getAllUsers(token: String): AppResult<List<UserProfile>, Throwable>
}

package com.example.learn_compose.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface Api {
    @GET
    suspend fun getTest(
        @Url url: String,
    ): List<MeetingsDto>

    @GET("/meetings")
    suspend fun getMeeting(): List<MeetingsDto>

    @POST("/register")
    suspend fun sendCode(
        @Body userPhoneNumber: UserPhoneNumberDto,
    ): ResponseMessageDto

    @POST("/verify-code")
    suspend fun verifyCode(
        @Body verificationCode: VerificationCodeDto,
    ): Boolean

    @POST("/submit-profile")
    suspend fun submitProfile(
        @Header("Authorization") token: String,
        @Body userProfile: UserProfileDto,
    ): Boolean

    @GET("/users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String,
    ): List<UserProfileDto>
}



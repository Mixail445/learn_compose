package com.example.learn_compose.data

import com.example.learn_compose.domain.RemoteSource
import com.example.learn_compose.utils.DispatchersProvider
import kotlinx.coroutines.withContext

class RemoteSourceImpl(
    private val api: Api,
    private val dispatchersProvider: DispatchersProvider,
) : RemoteSource {
    override suspend fun getMeetings(): List<MeetingsDto> =
        withContext(dispatchersProvider.io) {
            api.getMeeting()
        }

    override suspend fun getTest(url: String): List<MeetingsDto> =
        withContext(dispatchersProvider.io) {
            api.getTest(url)
        }

    override suspend fun sendCode(userPhoneNumber: UserPhoneNumberDto): ResponseMessageDto =
        withContext(dispatchersProvider.io) { api.sendCode(userPhoneNumber) }

    override suspend fun verifyCode(verificationCode: VerificationCodeDto): Boolean = api.verifyCode(verificationCode)

    override suspend fun submitProfile(
        token: String,
        userProfile: UserProfileDto,
    ): Boolean = api.submitProfile(token, userProfile)

    override suspend fun getAllUsers(token: String): List<UserProfileDto> = api.getAllUsers(token)
}

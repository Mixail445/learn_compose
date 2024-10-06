package com.example.learn_compose.data

import com.example.learn_compose.domain.Meetings
import com.example.learn_compose.domain.RemoteSource
import com.example.learn_compose.domain.Repository
import com.example.learn_compose.domain.ResponseMessage
import com.example.learn_compose.domain.UserPhoneNumber
import com.example.learn_compose.domain.UserProfile
import com.example.learn_compose.domain.VerificationCodeModel
import com.example.learn_compose.utils.AppResult
import com.example.learn_compose.utils.ResultWrapper
import javax.inject.Inject

class RepositoryImpl
    @Inject
    constructor(
        private val wrapper: ResultWrapper,
        private val remoteSource: RemoteSource,
    ) : Repository {
        override suspend fun getTest(url: String): AppResult<List<Meetings>, Throwable> =
            wrapper.wrap {
                remoteSource.getTest(url = url).map { it.mapToDomain() }
            }

        override suspend fun getMeetings(): AppResult<List<Meetings>, Throwable> =
            wrapper.wrap {
                remoteSource.getMeetings().map { it.mapToDomain() }
            }

        override suspend fun sendCode(userPhoneNumber: UserPhoneNumber): AppResult<ResponseMessage, Throwable> =
            wrapper.wrap {
                remoteSource.sendCode(userPhoneNumber.mapFromDto()).mapToDomain()
            }

        override suspend fun verifyCode(verificationCode: VerificationCodeModel): AppResult<Boolean, Throwable> =
            wrapper.wrap { remoteSource.verifyCode(verificationCode.mapToDto()) }

        override suspend fun submitProfile(
            token: String,
            userProfile: UserProfile,
        ): AppResult<Boolean, Throwable> =
            wrapper.wrap {
                remoteSource.submitProfile(token, userProfile.mapToDto())
            }

        override suspend fun getAllUsers(token: String): AppResult<List<UserProfile>, Throwable> =
            wrapper.wrap { remoteSource.getAllUsers(token).map { it.mapToDomain() } }
    }

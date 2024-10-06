package com.example.learn_compose.service

import kotlinx.serialization.Serializable

@Serializable
data class UserPhoneNumber(
    val phoneNumber: String,
)

@Serializable
data class VerificationCode(
    val code: String,
)

@Serializable
data class UserData(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
){
    fun mapToUser()= User(
        id,firstName,lastName, phoneNumber
    )
}

@Serializable
data class MeetingScreenData(
    val id: Int,
    val language: String,
    val level: String,
    val city: String,
    val date: String,
    val active: Boolean = false,
    val image: String,
    val list: List<String> = listOf("Python", "Junior", "Moscow"),
)

package com.example.learn_compose.ui.screens.registration.phone

import com.example.learn_compose.domain.UserPhoneNumber

data class UserPhoneNumberUi(
    val number: String
){
    fun mapFromDomain() = UserPhoneNumber(number)
}
package com.example.learn_compose.ui.screens.registration.profile

interface ProfileView {
    data class Model(
        val isButtonActive:Boolean = false,
        val name:String="",
        val nextName:String="",
    )
    sealed interface Event {
        data object OnClickButton: Event
    }
    sealed interface UiLabel{
        data object ShowScreenContainerWithButton: UiLabel
    }
}
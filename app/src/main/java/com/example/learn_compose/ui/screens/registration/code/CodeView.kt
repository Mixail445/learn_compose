package com.example.learn_compose.ui.screens.registration.code

interface CodeView {
    data class Model(
        var otpValue: String = ""
    )
    sealed interface Event {
        data object OnClickButton: Event
    }
    sealed interface UiLabel{
        data object ShowProfile: UiLabel
    }
}

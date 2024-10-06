package com.example.learn_compose.ui.screens.registration.phone

interface PhoneView {
    data class Model(
        val isButtonActive: Boolean = false,
        var phoneNumber: String,
        val loading: Boolean = false,
        val numberForm: NumberForm,
        val countyCode: String,
        val isErrorMessageServer: Boolean = true,
    )

    sealed interface Event {
        data object OnClickButton : Event
    }

    sealed interface UiLabel {
        data object ShowCodeScreen : UiLabel
    }
}

data class NumberForm(
    val phoneFormat: String,
    val codeFormat: String,
)

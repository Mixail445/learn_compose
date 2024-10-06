package com.example.learn_compose.ui.screens.detailMeeting

interface DetailView {
    data class Model(
        val bvText: String = "Пойду на встречу",
        val stateButton: StateButton = StateButton(),
    )

    sealed class Event {
        data object OnClickButton : Event()
    }

    sealed interface UiLabel
}

data class StateButton(
    val enabled: Boolean = true,
    val loading: Boolean = false,
    val secondary: Boolean = false,
)

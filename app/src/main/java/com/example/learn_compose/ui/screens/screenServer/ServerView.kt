package com.example.learn_compose.ui.screens.screenServer

interface ServerView {
    data class Model(
        val isStartServer: Boolean = false,
        val startService: Boolean = false,
    )

    sealed class Event {
        data object OnClickStart : Event()

        data object OnClickNextScreen : Event()
    }

    sealed class UiLabel {
        data object ShowScreenRegistration : UiLabel()
    }
}

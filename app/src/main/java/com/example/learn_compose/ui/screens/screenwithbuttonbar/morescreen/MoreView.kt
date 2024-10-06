package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen

interface MoreView {
    data class Model(
        val image: Int = 1,
        val numberPhone: Int = 2,
        val name: String = "Petro",
    )

    sealed class Event {
        data object OnClickMeetings : Event()

        data object OnClickProfile : Event()
    }

    sealed class UiLabel {
        data object ShowProfileScreen : UiLabel()

        data object ShowMeetingsScreen : UiLabel()
    }
}

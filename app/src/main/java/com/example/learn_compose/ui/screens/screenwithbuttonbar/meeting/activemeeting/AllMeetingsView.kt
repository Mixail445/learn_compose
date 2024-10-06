package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.activemeeting

import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi

interface AllMeetingsView {
    data class Model(
        val items: List<MeetingsUi> = emptyList(),
        val isRefresh: Boolean = false,
    )

    sealed class Event {
        data object OnClickItem : Event()
    }

    sealed class UiLabel {
        data object ShowDetailScreen : UiLabel()
    }
}

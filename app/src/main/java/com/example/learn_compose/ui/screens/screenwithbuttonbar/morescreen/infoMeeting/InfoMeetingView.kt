package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoMeeting

import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi

interface InfoMeetingView {
    data class Model(
        val item: List<MeetingsUi> = emptyList(),
    )

    sealed class Event {
        data object OnClickBack : Event()
    }

    sealed class UiLabel {
        data object ShowBackScreen : UiLabel()
    }
}

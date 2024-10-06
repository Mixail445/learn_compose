package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.allmeeting

import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi

interface ActiveMeetingView {
    data class Model(
        val activeItem: List<MeetingsUi> = emptyList(),
    )
}

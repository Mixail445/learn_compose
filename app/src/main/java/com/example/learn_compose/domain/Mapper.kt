package com.example.learn_compose.domain

import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi

fun Meetings.mapToUi() =
    MeetingsUi(
        language = language,
        level = level,
        city = city,
        date = date,
        active = active,
        image = image,
        listTag = listTag,
    )

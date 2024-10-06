package com.example.learn_compose.service

import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi


fun MeetingScreenData.toMeetings() = Meeting(id.toLong(), language, level, city, date, active, image, list)

fun Meeting.toMeetingScreenData(): MeetingScreenData =
    MeetingScreenData(
        id = this.id.toInt(),
        language = this.language,
        level = this.level,
        city = this.city,
        date = this.date,
        active = this.active,
        image = this.image,
        list = listOf("Python", "Junior", "Moscow"),
    )

fun MeetingScreenData.mapToUi()=
    MeetingsUi(
        language = this.language,
        level = this.level,
        city = this.city,
        date = this.date,
        active = this.active,
        image = this.image,
        listTag = listOf("Python", "Junior", "Moscow"),
    )
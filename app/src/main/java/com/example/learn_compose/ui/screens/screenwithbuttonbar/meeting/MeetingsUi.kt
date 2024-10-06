package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting

data class MeetingsUi(
    val language: String,
    val level: String,
    val city: String,
    val date: String,
    val active: Boolean = false,
    val image: String,
    val listTag: List<String>
)

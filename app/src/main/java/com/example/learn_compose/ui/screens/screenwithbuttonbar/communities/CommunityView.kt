package com.example.learn_compose.ui.screens.screenwithbuttonbar.communities

interface CommunityView {
    data class Model(
        val items: List<ItemMeetings> = emptyList(),
        val editText: String = "",
    )
}

data class ItemMeetings(
    val image: Int,
    val title: String,
    val bottomTitle: String,
)

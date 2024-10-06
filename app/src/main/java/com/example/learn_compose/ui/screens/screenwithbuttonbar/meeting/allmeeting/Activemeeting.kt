package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.allmeeting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learn_compose.ui.theme.components.ItemMeeting

@Composable
fun ActiveMeetingScreen(param: String) {
    val viewModel: ActiveMeetingVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(param) {
        viewModel.updateFilter(param)
    }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
    ) {
        uiState.activeItem.forEach {
            item {
                ItemMeeting(
                    image = it.image,
                    list = it.listTag,
                    date = it.date,
                    sity = it.city,
                    title = it.language,
                    onClick = {},
                )
            }
        }
    }
}

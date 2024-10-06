package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoMeeting

import androidx.lifecycle.ViewModel
import com.example.learn_compose.service.mapToUi
import com.example.learn_compose.service.title
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InfoMeetingVm
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(InfoMeetingView.Model())
        val uiState: StateFlow<InfoMeetingView.Model> = _uiState.asStateFlow()

        private val _uiLabels = MutableSharedFlow<InfoMeetingView.UiLabel>()
        val uiLabels: SharedFlow<InfoMeetingView.UiLabel> get() = _uiLabels

        private var _meetings = emptyList<MeetingsUi>()

        fun getData() {
            _meetings = title.map { it.mapToUi() }
            _uiState.update { it.copy(_meetings) }
        }

        suspend fun onEvent(event: InfoMeetingView.Event) {
            when (event) {
                InfoMeetingView.Event.OnClickBack -> handlerClickBack()
            }
        }

        private suspend fun handlerClickBack() {
            _uiLabels.emit(InfoMeetingView.UiLabel.ShowBackScreen)
        }

        fun update() {
            getData()
        }
    }

package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen

import androidx.lifecycle.ViewModel
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingView
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.activemeeting.AllMeetingsView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScreenMoreVm @Inject constructor():ViewModel() {
    private val _uiState = MutableStateFlow(MoreView.Model())
    val uiState: StateFlow<MoreView.Model> = _uiState.asStateFlow()

    private val _uiLabels = MutableSharedFlow<MoreView.UiLabel>()
    val uiLabels: SharedFlow<MoreView.UiLabel> get() = _uiLabels


    suspend fun onEvent(event: MoreView.Event) {
        when (event) {
            MoreView.Event.OnClickMeetings -> handlerClickMeetings()
            MoreView.Event.OnClickProfile -> handlerClickProfile()
        }
    }

    private suspend fun handlerClickProfile() {
        _uiLabels.emit(MoreView.UiLabel.ShowProfileScreen)
    }

    private suspend fun handlerClickMeetings() {
        _uiLabels.emit(MoreView.UiLabel.ShowMeetingsScreen)
    }
}
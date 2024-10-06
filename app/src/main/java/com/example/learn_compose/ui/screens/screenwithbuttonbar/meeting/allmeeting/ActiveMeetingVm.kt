package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.allmeeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn_compose.domain.Repository
import com.example.learn_compose.domain.mapToUi
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi
import com.example.learn_compose.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActiveMeetingVm
    @Inject
    constructor(
        private val repository: Repository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ActiveMeetingView.Model())
        val uiState: StateFlow<ActiveMeetingView.Model> = _uiState.asStateFlow()
        private var _meetings = emptyList<MeetingsUi>()

        init {
            getMeetings()
        }

        private fun getMeetings() {
            viewModelScope.launch {
                repository.getMeetings().onSuccess { data ->
                    _meetings = data.map { it.mapToUi() }
                }
                _uiState.update { it.copy(filter(_meetings, param = "")) }
            }
        }

        fun updateFilter(param: String) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(activeItem = filter(_meetings, param))
                }
            }
        }

        private fun filter(
            list: List<MeetingsUi>,
            param: String,
        ): List<MeetingsUi> =
            list.filter {
                it.active &&
                    (
                        it.city.contains(param, ignoreCase = true) ||
                            it.language.contains(param, ignoreCase = true)
                    )
            }
    }

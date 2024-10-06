package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MeetingVm
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(MeetingView.Model())
        val uiState: StateFlow<MeetingView.Model> = _uiState.asStateFlow()

        fun updateNextName(text: String) {
            _uiState.update { it.copy(editText = text) }
        }
    }

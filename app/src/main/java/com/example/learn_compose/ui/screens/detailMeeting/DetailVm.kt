package com.example.learn_compose.ui.screens.detailMeeting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailVm
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(DetailView.Model())
        val uiState: StateFlow<DetailView.Model> = _uiState.asStateFlow()
        private var isActive = true

        suspend fun onEvent(event: DetailView.Event) {
            when (event) {
                DetailView.Event.OnClickButton -> handlerClickItem()
            }
        }

        private fun handlerClickItem() {
            if (isActive) {
                isActive = false
                _uiState.update { state ->
                    state.copy(
                        bvText = "Схожу в другой раз",
                        stateButton = StateButton(secondary = true),
                    )
                }
            } else {
                isActive = true
                _uiState.update { state ->
                    state.copy(
                        bvText = "Пойду на встречу",
                        stateButton = StateButton(secondary = false),
                    )
                }
            }
        }
    }

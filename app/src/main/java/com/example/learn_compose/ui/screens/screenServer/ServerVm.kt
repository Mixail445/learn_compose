package com.example.learn_compose.ui.screens.screenServer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ServerVm
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(ServerView.Model())
        val uiState: StateFlow<ServerView.Model> = _uiState.asStateFlow()

        private val _uiLabels = MutableSharedFlow<ServerView.UiLabel>()
        val uiLabels: SharedFlow<ServerView.UiLabel> get() = _uiLabels

        suspend fun onEvent(event: ServerView.Event) {
            when (event) {
                ServerView.Event.OnClickStart -> handlerClickItem()
                ServerView.Event.OnClickNextScreen -> handlerClickNextScreen()
            }
        }

        private suspend fun handlerClickNextScreen() {
            _uiLabels.emit(ServerView.UiLabel.ShowScreenRegistration)
        }

        private var serverStart = false

        private fun handlerClickItem() {
            serverStart = !serverStart
            _uiState.update { it.copy(isStartServer = serverStart) }
        }
    }

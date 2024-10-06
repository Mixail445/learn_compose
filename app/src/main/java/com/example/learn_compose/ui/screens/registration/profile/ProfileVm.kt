package com.example.learn_compose.ui.screens.registration.profile

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
class ProfileVM @Inject constructor() : ViewModel() {
    private val _uiLabels = MutableSharedFlow<ProfileView.UiLabel>()
    val uiLabels: SharedFlow<ProfileView.UiLabel> get() = _uiLabels

    private val _uiState = MutableStateFlow(ProfileView.Model())
    val uiState: StateFlow<ProfileView.Model> = _uiState.asStateFlow()

    suspend fun onEvent(event: ProfileView.Event) {
        when (event) {
            ProfileView.Event.OnClickButton -> handleClickButton()
        }
    }

    private suspend fun handleClickButton() {
        _uiLabels.emit(ProfileView.UiLabel.ShowScreenContainerWithButton)
    }

    private fun updateButton() {
        _uiState.update { currentState ->
            currentState.copy(isButtonActive = currentState.name.isNotEmpty() && currentState.nextName.isNotEmpty())
        }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
        updateButton()
    }

    fun updateNextName(nextName: String) {
        _uiState.update { it.copy(nextName = nextName) }
        updateButton()
    }
}
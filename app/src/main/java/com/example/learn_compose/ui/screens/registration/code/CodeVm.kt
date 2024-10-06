package com.example.learn_compose.ui.screens.registration.code

import androidx.lifecycle.ViewModel
import com.example.learn_compose.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CodeVm @Inject constructor(val repository: Repository):ViewModel() {

    private val _uiLabels = MutableSharedFlow<CodeView.UiLabel>()
    val uiLabels: SharedFlow<CodeView.UiLabel> get() = _uiLabels

    private val _uiState = MutableStateFlow(CodeView.Model())
    val uiState: StateFlow<CodeView.Model> = _uiState.asStateFlow()

    fun updateOtpValue(newOtp: String) {
        _uiState.value = _uiState.value.copy(otpValue = newOtp)
    }

    suspend fun onEvent(event: CodeView.Event): Unit =
        when (event) {
            CodeView.Event.OnClickButton -> handlerClickButton()
        }

    private suspend fun handlerClickButton() {
        _uiLabels.emit(CodeView.UiLabel.ShowProfile)
    }
}
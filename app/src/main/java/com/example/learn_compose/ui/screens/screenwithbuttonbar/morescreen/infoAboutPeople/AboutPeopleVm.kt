package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoAboutPeople

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AboutPeopleVm @Inject constructor():ViewModel() {
    private val _uiState = MutableStateFlow(AboutPeopleView.Model())
    val uiState: StateFlow<AboutPeopleView.Model> = _uiState.asStateFlow()

    private val _uiLabels = MutableSharedFlow<AboutPeopleView.UiLabel>()
    val uiLabels: SharedFlow<AboutPeopleView.UiLabel> get() = _uiLabels


    suspend fun onEvent(event: AboutPeopleView.Event) {
        when (event) {
            AboutPeopleView.Event.OnClickBack -> handlerClickBack()
            AboutPeopleView.Event.OnClickEdit -> handlerClickEdit()
            AboutPeopleView.Event.OnClickLogOut -> handlerClickLogOut()
        }
    }

    private suspend fun handlerClickLogOut() {
        _uiLabels.emit(AboutPeopleView.UiLabel.ActionLogOut)
    }

    private fun handlerClickEdit() {
        TODO("Not yet implemented")
    }

    private suspend fun handlerClickBack() {
        _uiLabels.emit(AboutPeopleView.UiLabel.ShowBackScreen)
    }
}
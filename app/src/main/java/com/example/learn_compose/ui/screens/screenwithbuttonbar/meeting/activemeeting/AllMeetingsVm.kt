package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.activemeeting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn_compose.domain.Repository
import com.example.learn_compose.service.mapToUi
import com.example.learn_compose.service.title
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.MeetingsUi
import com.example.learn_compose.utils.onError
import com.example.learn_compose.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.http.Url
import javax.inject.Inject

@HiltViewModel
class AllMeetingsVm
    @Inject
    constructor(
        private val repository: Repository,
    ) : ViewModel() {
        private val _uiLabels = MutableSharedFlow<AllMeetingsView.UiLabel>()
        val uiLabels: SharedFlow<AllMeetingsView.UiLabel> get() = _uiLabels

        private val _uiState = MutableStateFlow(AllMeetingsView.Model())
        val uiState: StateFlow<AllMeetingsView.Model> = _uiState.asStateFlow()
        private var _meetings = emptyList<MeetingsUi>()

        init {
            getData()
        }

        private fun getData() {

            _meetings = title.map { it.mapToUi() }
            viewModelScope.launch {
                repository.getTest(url ="http://192.168.0.100:8080/" ).onSuccess { Log.d("213",it.toString()) }.onError { Log.d("error",it.message.toString()) }
                repository.getMeetings().onSuccess { data ->
                    _meetings = title.map { it.mapToUi() }

                    // _meetings = data.map { it.mapToUi() }
                }
                _uiState.update {
                    it.copy(items = _meetings)
                }
            }
        }

        fun refreshing() {
            getData()
        }

        fun updateFilter(param: String) {
            viewModelScope.launch {
                val filteredMeetings =
                    _meetings.filter {
                        it.city.contains(param, ignoreCase = true) ||
                            it.language.contains(param, ignoreCase = true)
                    }
                _uiState.emit(AllMeetingsView.Model(filteredMeetings))
            }
        }

        suspend fun onEvent(event: AllMeetingsView.Event) {
            when (event) {
                AllMeetingsView.Event.OnClickItem -> handlerClickItem()
            }
        }

        private suspend fun handlerClickItem() {
            _uiLabels.emit(AllMeetingsView.UiLabel.ShowDetailScreen)
        }
    }

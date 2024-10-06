package com.example.learn_compose.ui.screens.screenwithbuttonbar.communities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learn_compose.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityVm
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(CommunityView.Model())
        val uiState: StateFlow<CommunityView.Model> = _uiState.asStateFlow()

        fun updateNextName(text: String) {
            _uiState.update { it.copy(editText = text) }
        }

        init {
            _uiState.update {
                it.copy(items = filter(mockRepository(), ""))
            }
        }

        fun updateFilter(param: String) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(items = filter(mockRepository(), param))
                }
            }
        }

        private fun filter(
            list: List<ItemMeetings>,
            param: String,
        ): List<ItemMeetings> =
            list.filter {
                it.title.contains(param, ignoreCase = true) ||
                    it.bottomTitle.contains(param, ignoreCase = true)
            }

        private fun mockRepository(): List<ItemMeetings> =
            listOf(
                ItemMeetings(R.drawable.images, "Desigma", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.avatar, "NoInline", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.book_text_im, "OfetNext", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.foto_frame_3315, "323Fer", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.images, "Desigma", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.avatar, "NoInline", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.book_text_im, "OfetNext", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.foto_frame_3315, "323Fer", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.images, "Desigma", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.avatar, "NoInline", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.book_text_im, "OfetNext", (0..1000).random().toString()+" человек"),
                ItemMeetings(R.drawable.foto_frame_3315, "323Fer", (0..1000).random().toString()+" человек"),
            )
    }

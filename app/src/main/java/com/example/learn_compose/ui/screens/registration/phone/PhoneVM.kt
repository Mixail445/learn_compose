package com.example.learn_compose.ui.screens.registration.phone

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.learn_compose.domain.Repository
import com.example.learn_compose.utils.getMaskForCountry
import com.example.learn_compose.utils.onError
import com.example.learn_compose.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PhoneVM
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        val repository: Repository,
    ) : ViewModel() {
        private var _phoneNumber: String
            get() = savedStateHandle["phoneNumber"] ?: ""
            set(value) {
                savedStateHandle["phoneNumber"] = value
            }
        private val _uiState =
            MutableStateFlow(
                PhoneView.Model(
                    isButtonActive = false,
                    phoneNumber = _phoneNumber,
                    loading = false,
                    numberForm = NumberForm("+7", "000 000-00-00"),
                    countyCode = "",
                ),
            )
    init {
        _phoneNumber = "324234234"
    }

        val uiState: StateFlow<PhoneView.Model> = _uiState.asStateFlow()

        private val _uiLabels = MutableSharedFlow<PhoneView.UiLabel>()
        val uiLabels: SharedFlow<PhoneView.UiLabel> get() = _uiLabels

        suspend fun onEvent(event: PhoneView.Event): Unit =
            when (event) {
                PhoneView.Event.OnClickButton -> handlerClickButton()
            }

        private suspend fun handlerClickButton() {
            _uiLabels.emit(PhoneView.UiLabel.ShowCodeScreen)
            setData(UserPhoneNumberUi("34534534"))
        }

        private suspend fun setData(userPhoneNumberUi: UserPhoneNumberUi) {
            repository
                .sendCode(userPhoneNumberUi.mapFromDomain())
                .onSuccess { Log.d("succes", "succes") }
                .onError { Log.d("error", it.message.toString()) }
        }

        fun updatePhoneNumber(newNumber: String) {
            _phoneNumber = newNumber
            _uiState.value =
                _uiState.value.copy(
                    phoneNumber = _phoneNumber,
                    isButtonActive = isValidPhoneNumber(newNumber),
                )
        }

        fun updateCountry(countryCode: String) {
            val (mask, placeholder) = getMaskForCountry(countryCode)
            _uiState.update {
                it.copy(
                    countyCode = countryCode,
                    numberForm = NumberForm(mask, placeholder),
                    isButtonActive = false,
                )
            }
        }

        private fun isValidPhoneNumber(number: String): Boolean = number.length in 8..15
    }

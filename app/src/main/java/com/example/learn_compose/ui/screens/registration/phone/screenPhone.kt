package com.example.learn_compose.ui.screens.registration.phone

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.theme.components.ButtonBase
import com.example.learn_compose.ui.theme.components.CountryMenu
import com.example.learn_compose.ui.theme.components.PhoneInputField
import com.example.learn_compose.utils.LaunchAndRepeatWithLifecycle
import com.example.learn_compose.utils.Subscribe
import com.example.learn_compose.utils.formatNumberWithCodeCountry
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PhoneScreen(navController: NavController) {
    val viewModel = hiltViewModel<PhoneVM>()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    fun handleUiLabel(uiLabel: PhoneView.UiLabel): Unit =
        when (uiLabel) {
            PhoneView.UiLabel.ShowCodeScreen ->
                navController.navigate(
                    "${Screen.Code.screenName}/${uiState.countyCode + " "}${
                        formatNumberWithCodeCountry(
                            uiState.countyCode,
                            uiState.phoneNumber,
                        )
                    }",
                )
        }
    LaunchedEffect(uiState.countyCode) {
        if (uiState.countyCode.isEmpty()) {
            viewModel.updateCountry("+7")
        }
    }

    Subscribe(viewModel.uiState) { _ ->
        // maybe no used
    }

    LaunchAndRepeatWithLifecycle {
        viewModel.uiLabels.collect { label ->
            handleUiLabel(label)
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.title_main), fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.title_child),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(49.dp))

            Row(
                Modifier
                    .background(Color.Transparent)
                    .height(36.dp),
            ) {
                CountryMenu(
                    modifier =
                        Modifier
                            .height(40.dp)
                            .background(
                                Color(0xFFF7F7FC),
                                RoundedCornerShape(percent = 4),
                            ).align(Alignment.CenterVertically)
                            .padding(4.dp),
                    onClick = { viewModel.updateCountry(it) },
                )

                Spacer(modifier = Modifier.width(8.dp))

                PhoneInputField(
                    modifier =
                        Modifier
                            .background(Color(0xFFF7F7FC), RoundedCornerShape(percent = 4))
                            .padding(4.dp)
                            .height(36.dp),
                    fontSize = 14.sp,
                    mask = uiState.numberForm.phoneFormat,
                    placeholderText = uiState.numberForm.codeFormat,
                    onValueChange = { newValue -> viewModel.updatePhoneNumber(newValue.toString()) },
                )
            }

            Spacer(modifier = Modifier.height(69.dp))

            ButtonBase(
                text = stringResource(R.string.bv_text_next),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                enabled = uiState.isButtonActive,
                onClick = {
                    coroutineScope.launch { viewModel.onEvent(PhoneView.Event.OnClickButton) }
                },
            )
        }
    }
}

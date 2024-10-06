package com.example.learn_compose.ui.screens.registration.code

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.theme.components.ButtonBase
import com.example.learn_compose.ui.theme.components.OtpTextField
import com.example.learn_compose.utils.LaunchAndRepeatWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun ScreenCode(number: String, navController: NavController) {
    val viewModel: CodeVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    fun handleUiLabel(uiLabel: CodeView.UiLabel): Unit =
        when (uiLabel) {
            CodeView.UiLabel.ShowProfile ->  navController.navigate(Screen.Profile.screenName)
        }
    LaunchAndRepeatWithLifecycle {
        viewModel.uiLabels.collect { label ->
            handleUiLabel(label)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = stringResource(R.string.screen_code_title_main), fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.screen_code_child, number), fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(49.dp))

            OtpTextField(
                otpCount = 4,
                otpText = uiState.otpValue,
                onOtpTextChange = { value, otpInputFilled ->
                    viewModel.updateOtpValue(value)
                    if (otpInputFilled) {
                        coroutineScope.launch {
                            viewModel.onEvent(CodeView.Event.OnClickButton)
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(height = 40.dp)
            )

            Spacer(modifier = Modifier.height(69.dp))
            ButtonBase(text = stringResource(R.string.screen_code_bv_text), secondary = true, onClick = {
                Toast.makeText(context, "Request code", Toast.LENGTH_SHORT).show()
            })
        }
    }
}

package com.example.learn_compose.ui.screens.registration.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.theme.components.ButtonBase
import com.example.learn_compose.ui.theme.components.NameFamilyTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile(navController: NavController) {
    val viewModel: ProfileVM = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.uiLabels) {
        viewModel.uiLabels.collect { label ->
            when (label) {
                ProfileView.UiLabel.ShowScreenContainerWithButton -> navController.navigate(Screen.Bottom.screenName)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                    ),
                title = {
                    Text(
                        text = stringResource(R.string.screen_profile_text),
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 18.sp,
                    )
                },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it),
        ) {
            Spacer(modifier = Modifier.height(46.dp))
            Image(
                painter = painterResource(R.drawable.avatar),
                contentDescription = "ere",
                Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(31.dp))
            NameFamilyTextField(
                textStyle = TextStyle(fontSize = 14.sp),
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(36.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                placeholderText = stringResource(R.string.screen_profile_name),
                onValueChange = viewModel::updateName,
            )
            Spacer(modifier = Modifier.height(12.dp))
            NameFamilyTextField(
                textStyle = TextStyle(fontSize = 14.sp),
                modifier =
                    Modifier
                        .height(36.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                placeholderText = stringResource(R.string.screen_profile_nextName),
                onValueChange = viewModel::updateNextName,
            )
            Spacer(modifier = Modifier.height(46.dp))
            ButtonBase(
                text = stringResource(R.string.screen_profile_bv_save),
                modifier =
                    Modifier
                        .padding(horizontal = 24.dp)
                        .height(52.dp),
                enabled = uiState.isButtonActive,
                onClick = { coroutineScope.launch { viewModel.onEvent(ProfileView.Event.OnClickButton) } },
            )
        }
    }
}

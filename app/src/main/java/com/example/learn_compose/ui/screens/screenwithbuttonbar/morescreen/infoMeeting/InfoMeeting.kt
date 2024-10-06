package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoMeeting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoAboutPeople.AboutPeopleView
import com.example.learn_compose.ui.theme.components.ItemMeeting
import kotlinx.coroutines.launch

@Composable
fun ScreenInfoMeetings(navController:NavController) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: InfoMeetingVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.item) {
        viewModel.update()
    }
    LaunchedEffect(viewModel.uiLabels) {
        viewModel.uiLabels.collect { label ->
            when (label) {
                InfoMeetingView.UiLabel.ShowBackScreen -> navController.popBackStack()
            }
        }
    }
    Scaffold(
        topBar = {
            Row(Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back_),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .padding(start = 16.dp, top = 14.dp)
                            .clickable { coroutineScope.launch { viewModel.onEvent(InfoMeetingView.Event.OnClickBack) } },
                )
                Text(
                    text = "Профиль",
                    modifier =
                        Modifier
                            .padding(start = 8.dp, end = 24.dp, bottom = 13.dp, top = 14.dp)
                            .height(29.dp)
                            .weight(1f),
                    style =
                        TextStyle.Default.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                        ),
                )
            }
        },
    ) { t ->
        LazyColumn(Modifier.fillMaxSize().padding(t)) {
            uiState.item.forEach {
                item {
                    ItemMeeting(
                        list = it.listTag,
                        image = it.image,
                        date = it.date,
                        it.city,
                        it.language,
                        onClick = {},
                    )
                }
            }
        }
    }
}

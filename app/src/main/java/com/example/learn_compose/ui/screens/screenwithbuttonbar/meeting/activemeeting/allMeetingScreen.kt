package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.activemeeting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.theme.components.ItemMeeting
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllMeetingScreen(
    param: String,
    navController: NavController,
) {
    val viewModel: AllMeetingsVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(uiState.isRefresh, { viewModel.refreshing() })
    LaunchedEffect(param) {
        viewModel.updateFilter(param)
    }

    LaunchedEffect(viewModel.uiLabels) {
        viewModel.uiLabels.collect { label ->
            when (label) {
                AllMeetingsView.UiLabel.ShowDetailScreen -> navController.navigate(Screen.DetailMeeting.screenName) //
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            uiState.items.forEach {
                item {
                    ItemMeeting(
                        list = it.listTag,
                        image = it.image,
                        date = it.date,
                        it.city,
                        it.language,
                        onClick = {
                            coroutineScope.launch { viewModel.onEvent(AllMeetingsView.Event.OnClickItem) }
                        },
                    )
                }
            }
        }
        PullRefreshIndicator(
            uiState.isRefresh,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter),
        )
    }
}

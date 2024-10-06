package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.theme.components.ItemSetting
import com.example.learn_compose.ui.theme.components.ProfileCard
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ScreenMore(navController: NavController) {
    val viewModel: ScreenMoreVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.uiLabels) {
        viewModel.uiLabels.collect { label ->
            when (label) {
                MoreView.UiLabel.ShowMeetingsScreen -> navController.navigate(Screen.MoreMeetings.screenName)
                MoreView.UiLabel.ShowProfileScreen -> navController.navigate(Screen.MoreProfile.screenName)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.more),
                            modifier =
                                Modifier
                                    .padding(start = 24.dp, end = 24.dp, bottom = 13.dp, top = 14.dp)
                                    .height(29.dp),
                            style =
                                TextStyle.Default.copy(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(600),
                                ),
                        )
                    },
                    backgroundColor = Color.White,
                    // the top bar has an indentation on the left by default, I'm shifting it
                    modifier =
                        Modifier.layout { measurable, constraints ->
                            val paddingCompensation = 16.dp.toPx().roundToInt()
                            val adjustedConstraints =
                                constraints.copy(
                                    maxWidth = constraints.maxWidth + paddingCompensation,
                                )
                            val placeable = measurable.measure(adjustedConstraints)
                            layout(placeable.width, placeable.height) {
                                placeable.place(-paddingCompensation / 2, 0)
                            }
                        },
                    elevation = 0.dp,
                )
            }
            item {
                ProfileCard(onClick = {
                    coroutineScope.launch { viewModel.onEvent(MoreView.Event.OnClickProfile) }
                })
            }
            item { ItemSetting(onClick = { coroutineScope.launch { viewModel.onEvent(MoreView.Event.OnClickMeetings) } }) }
            item {
                Divider(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp)
                            .height(2.dp)
                            .background(Color(0xFFEDEDED)),
                )
            }
            item {
                ItemSetting(
                    iconStart = R.drawable.icon_sun,
                    nameSetting = "Тема",
                    onClick = {},
                )
            }
        }
    }
}

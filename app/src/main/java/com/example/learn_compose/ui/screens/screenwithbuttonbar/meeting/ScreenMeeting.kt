package com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.screens.detailMeeting.ScreenDetailMeeting
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.activemeeting.AllMeetingScreen
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.allmeeting.ActiveMeetingScreen
import com.example.learn_compose.ui.theme.components.NameFamilyTextField

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ScreenMeeting() {
    val bottomItems = listOf(Screen.MeetingAll, Screen.MeetingActive)
    val viewModel: MeetingVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route
    Log.d("ScreenMeeting", navController.toString())
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = currentRoute != Screen.DetailMeeting.screenName,
                enter =
                    slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 100),
                    ),
                exit =
                    slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 300),
                    ),
            ) {
                Column {
                    Text(
                        text = "Встречи",
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
                    NameFamilyTextField(
                        spaseBetweenImageAndText = 8.dp,
                        textStyle =
                            TextStyle(
                                fontWeight = FontWeight(600),
                                color = Color(0xFFADB5BD),
                            ),
                        placeholderText = "Поиск",
                        leadingIcon = {
                            Icon(
                                tint = Color(0xFFADB5BD),
                                painter = painterResource(id = R.drawable.icon__2_),
                                contentDescription = "",
                            )
                        },
                        onValueChange = { viewModel.updateNextName(it) },
                        modifier =
                            Modifier
                                .height(36.dp)
                                .fillMaxWidth()
                                .background(Color(0xFFF7F7FC))
                                .padding(horizontal = 8.dp),
                    )
                    Row(
                        modifier =
                            Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        bottomItems.forEach { screen ->
                            Column(
                                modifier =
                                    Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clickable {
                                            navController.navigate(screen.screenName) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                    inclusive = false
                                                }
                                                launchSingleTop = true
                                            }
                                        },
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = stringResource(id = screen.titleResourceId).uppercase(),
                                        style =
                                            TextStyle.Default.copy(
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight(500),
                                                color =
                                                    if (currentRoute == screen.screenName) {
                                                        Color(0xFF9A41FE)
                                                    } else {
                                                        Color.Gray
                                                    },
                                            ),
                                        modifier = Modifier.align(Alignment.Center),
                                    )
                                }

                                if (currentRoute == screen.screenName) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .align(Alignment.End)
                                                .height(2.dp)
                                                .fillMaxWidth()
                                                .background(Color(0xFF9A41FE)),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MeetingAll.screenName,
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
        ) {
            composable(Screen.MeetingActive.screenName) { ActiveMeetingScreen(uiState.editText) }
            composable(Screen.MeetingAll.screenName) {
                AllMeetingScreen(uiState.editText, navController)
            }
            composable(Screen.DetailMeeting.screenName) {
                ScreenDetailMeeting(navController)
            }
        }
    }
}

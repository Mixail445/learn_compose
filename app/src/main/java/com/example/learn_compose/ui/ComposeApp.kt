package com.example.learn_compose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.screens.registration.code.ScreenCode
import com.example.learn_compose.ui.screens.registration.phone.PhoneScreen
import com.example.learn_compose.ui.screens.registration.profile.ScreenProfile
import com.example.learn_compose.ui.screens.screenServer.ServerScreen
import com.example.learn_compose.ui.screens.screenwithbuttonbar.communities.ScreenCommunity
import com.example.learn_compose.ui.screens.screenwithbuttonbar.meeting.ScreenMeeting
import com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.ScreenMore
import com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoAboutPeople.ScreenAboutPeople
import com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoMeeting.ScreenInfoMeetings

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    Scaffold {
        NavHost(navController = navController, startDestination = Screen.Server.screenName) {
            composable(Screen.Server.screenName) { ServerScreen(navController) }
            composable(Screen.Number.screenName) { PhoneScreen(navController) }
            composable("${Screen.Code.screenName}/{phoneNumber}") { backStackEntry ->
                val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
                ScreenCode(phoneNumber, navController)
            }
            composable(Screen.Profile.screenName) { ScreenProfile(navController = navController) }
            composable(Screen.Bottom.screenName) { Container() }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Container() {
    val bottomItems = listOf(Screen.Meeting, Screen.Communities, Screen.More)
    val navController = rememberNavController()
    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route
    val icon = listOf(R.drawable.icon_meeting, R.drawable.icon_community, R.drawable.icon_more)
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                elevation = (-10).dp,
                modifier = Modifier.navigationBarsPadding(),
            ) {
                bottomItems.forEachIndexed { index, screen ->
                    BottomNavigationItem(
                        selectedContentColor = Color.Transparent,
                        alwaysShowLabel = false,
                        selected = currentRoute == screen.screenName,
                        onClick = {
                            navController.navigate(screen.screenName) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                    inclusive = false
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(id = screen.titleResourceId),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                )
                                if (currentRoute == screen.screenName) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Box(
                                        modifier =
                                            Modifier
                                                .size(6.dp)
                                                .background(Color.Black, shape = CircleShape)
                                                .align(Alignment.CenterHorizontally),
                                    )
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = icon[index]),
                                contentDescription = null,
                                tint = if (currentRoute == screen.screenName) Color.Transparent else Color.Black,
                            )
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Meeting.screenName,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Meeting.screenName) { ScreenMeeting() }
            composable(Screen.Communities.screenName) { ScreenCommunity() }
            composable(Screen.More.screenName) { ContainMore() }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContainMore() {
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.More.screenName,
        ) {
            composable(Screen.More.screenName) { ScreenMore(navController) }
            composable(Screen.MoreMeetings.screenName) { ScreenInfoMeetings(navController) }
            composable(Screen.MoreProfile.screenName) { ScreenAboutPeople(navController) }
        }
    }
}

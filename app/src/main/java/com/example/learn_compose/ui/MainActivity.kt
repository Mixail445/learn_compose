package com.example.learn_compose.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learn_compose.service.NotificationPermissionManager
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.theme.Learn_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationManager = NotificationPermissionManager(this)
        notificationManager.checkNotificationPermission()
        setContent {
            enableEdgeToEdge(
                statusBarStyle =
                    SystemBarStyle.light(
                        scrim = Color.White.value.toInt(),
                        darkScrim = Color.Transparent.value.toInt(),
                    ),
                navigationBarStyle =
                    SystemBarStyle.light(
                        scrim = Color.White.value.toInt(),
                        darkScrim = Color.Transparent.value.toInt(),
                    ),
            )
            Learn_composeTheme(darkTheme = false) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Number.screenName,
                ) {
                    composable(Screen.Number.screenName) {
                        ComposeApp()
                    }
                }
            }
        }
    }
}

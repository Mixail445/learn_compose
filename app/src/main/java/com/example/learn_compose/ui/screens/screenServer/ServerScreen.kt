package com.example.learn_compose.ui.screens.screenServer

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.service.KtorService
import com.example.learn_compose.ui.navigation.Screen
import com.example.learn_compose.ui.screens.registration.phone.PhoneScreen
import com.example.learn_compose.ui.theme.components.ButtonBase
import com.example.learn_compose.ui.theme.components.NameFamilyTextField
import com.example.learn_compose.utils.LaunchAndRepeatWithLifecycle
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.util.InetAddressUtils
import kotlinx.coroutines.launch
import java.net.NetworkInterface

// https://192.168.0.104:8080/
const val BASE_URL = " http://192.168.0.100:8080/"
const val BASE_URL_NOTHING = "http://192.168.0.104:8080/"

@Preview
@Composable
fun ServerScreen(navController: NavController? = null) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<ServerVm>()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.isStartServer) {
        val intent = Intent(context, KtorService::class.java)
        intent.putExtra("startKtor", uiState.isStartServer)
        context.applicationContext.startService(intent)
    }

    fun handleUiLabel(uiLabel: ServerView.UiLabel): Unit? =
        when (uiLabel) {
            ServerView.UiLabel.ShowScreenRegistration -> navController?.navigate(Screen.Number.screenName)
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
                .padding(horizontal = 16.dp)
                .paint(
                    painter = painterResource(id = R.drawable.book_text_im),
                    contentScale = ContentScale.Crop,
                ),
    ) {
        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!uiState.isStartServer) {
                ButtonBase(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Start/stop Server",
                    onClick = {
                        coroutineScope.launch {
                            viewModel.onEvent(ServerView.Event.OnClickStart)
                        }
                    },
                )
                ButtonBase(text = "Im not server", onClick = {
                    coroutineScope.launch { viewModel.onEvent(ServerView.Event.OnClickNextScreen) }
                })
            } else {
                Text(
                    text = "Ваш id:${getIPAddress(useIPv4 = true)}",
                    color = Color.Blue,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(700),
                )
                Text(
                    text = "Введите ip вашего телефона",
                    color = Color.Blue,
                    fontSize = 26.sp,
                    fontWeight = FontWeight(700),
                )
                Spacer(modifier = Modifier.height(20.dp))
                NameFamilyTextField(
                    onValueChange = {},
                    modifier =
                        Modifier.height(30.dp).background(
                            Color.LightGray,
                        ),
                    placeholderText = "   ip:",
                    textStyle = TextStyle(fontSize = 15.sp),
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonBase(text = "Next", onClick = {
                    coroutineScope.launch { viewModel.onEvent(ServerView.Event.OnClickNextScreen) }
                })
            }
        }
    }
}
//go to utils
fun getIPAddress(useIPv4: Boolean): String {
    return try {
        val interfaces = NetworkInterface.getNetworkInterfaces().toList()
        for (intf in interfaces) {
            val addrs = intf.inetAddresses.toList()
            for (addr in addrs) {
                if (!addr.isLoopbackAddress) {
                    val sAddr = addr.hostAddress.uppercase()
                    val isIPv4 = InetAddressUtils.isIPv4Address(sAddr)
                    if (useIPv4) {
                        if (isIPv4) return sAddr
                    } else {
                        if (!isIPv4) {
                            val delim = sAddr.indexOf('%') // drop ip6 port suffix
                            return if (delim < 0) sAddr else sAddr.substring(0, delim)
                        }
                    }
                }
            }
        }
        ""
    } catch (ex: Exception) {
        "" // for now eat exceptions
    }
}

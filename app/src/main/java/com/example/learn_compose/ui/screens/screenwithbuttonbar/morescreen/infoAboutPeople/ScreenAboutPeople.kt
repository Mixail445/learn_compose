package com.example.learn_compose.ui.screens.screenwithbuttonbar.morescreen.infoAboutPeople

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.theme.components.ButtonBase
import com.example.learn_compose.ui.theme.components.ButtonItem
import com.example.learn_compose.ui.theme.components.ButtonList
import kotlinx.coroutines.launch

@Composable
fun ScreenAboutPeople(navController: NavController) {
    val viewModel: AboutPeopleVm = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.uiLabels) {
        viewModel.uiLabels.collect { label ->
            when (label) {
                AboutPeopleView.UiLabel.ActionLogOut -> navController.popBackStack()
                AboutPeopleView.UiLabel.ShowBackScreen -> navController.popBackStack()
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
                            .clickable { coroutineScope.launch { viewModel.onEvent(AboutPeopleView.Event.OnClickBack) } },
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
                Icon(
                    painter = painterResource(id = R.drawable.icon_group),
                    contentDescription = "",
                    modifier = Modifier.padding(end = 16.dp, top = 14.dp),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "",
                modifier =
                    Modifier
                        .padding(top = 46.dp)
                        .size(200.dp),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 20.dp),
            ) {
                Text(
                    text = "Иван Иванов",
                    style =
                        TextStyle(
                            fontWeight = FontWeight(600),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                        ),
                )
                Text(
                    text = "+7 999 999-99-99",
                    color = Color(0xFFADB5BD),
                    style = TextStyle(fontWeight = FontWeight(400)),
                )
                Spacer(modifier = Modifier.height(40.dp))
                val list =
                    listOf(
                        ButtonList(R.drawable.icon_twitter, "1"),
                        ButtonList(R.drawable.icon_instagram, "1"),
                        ButtonList(R.drawable.icon_linkedin, "1"),
                        ButtonList(R.drawable.icon_facebook, "1"),
                    )
                ButtonItem(buttonList = list, modifier = Modifier.padding(horizontal = 26.dp))
                Spacer(modifier = Modifier.height(30.dp))
                ButtonBase(
                    text = "Logout",
                    modifier = Modifier.padding(horizontal = 26.dp),
                    onClick = { coroutineScope.launch { viewModel.onEvent(AboutPeopleView.Event.OnClickLogOut) } },
                )
            }
        }
    }
}

package com.example.learn_compose.ui.screens.screenwithbuttonbar.communities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learn_compose.R
import com.example.learn_compose.ui.theme.components.ItemCommunity
import com.example.learn_compose.ui.theme.components.NameFamilyTextField
import kotlinx.coroutines.launch

@Preview
@Composable
fun ScreenCommunity() {
    val viewModel: CommunityVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(uiState.editText) {
        coroutineScope.launch {
            viewModel.updateFilter(uiState.editText)
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
    ) {
        Text(
            text = stringResource(R.string.screen_community_title_meeting),
            modifier =
                Modifier
                    .padding(end = 24.dp, bottom = 13.dp, top = 14.dp)
                    .height(29.dp),
            style = TextStyle.Default.copy(fontSize = 18.sp, fontWeight = FontWeight(600)),
        )
        NameFamilyTextField(
            spaseBetweenImageAndText = 8.dp,
            textStyle =
                TextStyle(
                    fontWeight = FontWeight(600),
                    color = Color(0xFFADB5BD),
                ),
            placeholderText = stringResource(R.string.screen_community_search_title),
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
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
        ) {
            item {
                uiState.items.forEach {
                    ItemCommunity(title = it.title, bottomTitle = it.bottomTitle, image = it.image)
                }
            }
        }
    }
}

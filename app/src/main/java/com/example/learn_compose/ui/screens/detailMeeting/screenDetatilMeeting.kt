package com.example.learn_compose.ui.screens.detailMeeting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.learn_compose.R
import com.example.learn_compose.ui.theme.components.ButtonBase
import com.example.learn_compose.ui.theme.components.ImageOverlayList
import com.example.learn_compose.ui.theme.components.TagsBut
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenDetailMeeting(navController: NavController) {
    val viewModel: DetailVm = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        content = {
            stickyHeader {
                Row(
                    modifier = Modifier.height(30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier =
                            Modifier.size(24.dp).clickable {
                                navController.popBackStack()
                            },
                        painter = painterResource(id = R.drawable.icon_back_),
                        contentDescription = "",
                    )
                    Text(
                        text = "Developer meeting",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        style =
                            TextStyle(
                                fontWeight = FontWeight(600),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            ),
                    )
                    if (uiState.stateButton.secondary) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_done_24),
                            contentDescription = "",
                            tint = Color(0xFF9A41FE),
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(29.dp)) }
            item {
                Row {
                    Text(
                        text = "13.09.2024 ",
                        color = Color(0xFFA4A4A4),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                    )
                    Text(
                        text = "-",
                        color = Color(0xFFA4A4A4),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                    )
                    Text(
                        text = "Москва, ул. Громова, 4",
                        color = Color(0xFFA4A4A4),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                    )
                }
            }

            val list = listOf("Python", "Junior", "Moscow")
            item {
                Spacer(modifier = Modifier.height(8.dp))
                TagsBut(list)
            }
            item {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth() // Занять всю ширину
                            .padding(top = 16.dp), // Отступ сверху
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.image_map),
                        contentDescription = "",
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(175.dp)
                                .align(Alignment.Center) // Выравнивание по центру
                                .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.screen_detail_long_text),
                    style =
                        TextStyle(
                            color = Color(0xFFA4A4A4),
                            fontWeight = FontWeight(400),
                        ),
                    maxLines = 8,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                )
            }

            val listImage =
                listOf(
                    R.drawable.images,
                    R.drawable.images,
                    R.drawable.images,
                    R.drawable.images,
                    R.drawable.images,
                )
            item {
                Spacer(modifier = Modifier.height(20.dp))
                ImageOverlayList(listImage, 11)
            }
            item {
                Spacer(modifier = Modifier.height(13.dp))
                ButtonBase(
                    text = uiState.bvText,
                    enabled = uiState.stateButton.enabled,
                    secondary = uiState.stateButton.secondary,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.onEvent(DetailView.Event.OnClickButton)
                        }
                    },
                )
            }
        },
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 14.dp),
    )
}

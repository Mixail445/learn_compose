package com.example.learn_compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun LaunchAndRepeatWithLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    block: suspend CoroutineScope.() -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            coroutineScope.launch {
                block()
            }
        }
    }
}

@Composable
fun <T> Subscribe(
    flow: StateFlow<T>,
    onNext: (t: T) -> Unit,
) {
    val state by flow.collectAsState()

    LaunchedEffect(state) {
        onNext(state)
    }
}
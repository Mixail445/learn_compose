package com.example.learn_compose.utils

import javax.inject.Inject

class ResultWrapper
    @Inject
    constructor() {
        @SuppressWarnings("TooGenericExceptionCaught")
        suspend fun <R> wrap(request: suspend () -> R): AppResult<R, Throwable> {
            val result =
                try {
                    AppResult.Success(request())
                } catch (e: Throwable) {
                    AppResult.Error(e)
                }
            return result
        }
    }

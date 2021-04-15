package com.thousand.bosch.global.utils

import com.thousand.bosch.global.system.ResourceManager


class ErrorHandler(private val resourceManager: ResourceManager) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        //messageListener(error.errorMessage(resourceManager))
    }
}
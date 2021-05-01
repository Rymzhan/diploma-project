package com.diploma.stats.global.utils

import com.diploma.stats.global.system.ResourceManager


class ErrorHandler(private val resourceManager: ResourceManager) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit = {}) {
        //messageListener(error.errorMessage(resourceManager))
    }
}
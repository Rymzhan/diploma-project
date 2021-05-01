package com.diploma.stats.global.functional

import android.content.Context
import com.diploma.stats.global.extension.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected

}
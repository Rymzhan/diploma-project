package com.thousand.bosch.global.functional

import android.content.Context
import com.thousand.bosch.global.extension.networkInfo

class NetworkHandler(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected

}
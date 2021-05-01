package com.diploma.stats.global.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

internal fun Context.getColorResource(colorResource: Int): Int =
    ContextCompat.getColor(this, colorResource)
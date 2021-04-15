package com.thousand.bosch.global.extension


fun Int?.addZeroOnFirst(): String = String.format("%02d", this)


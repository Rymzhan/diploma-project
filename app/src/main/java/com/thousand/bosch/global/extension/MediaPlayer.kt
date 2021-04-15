package com.thousand.bosch.global.extension

import android.media.MediaPlayer

val MediaPlayer.seconds: Int
    get() {
        return this.duration.div(1000)
    }

val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }
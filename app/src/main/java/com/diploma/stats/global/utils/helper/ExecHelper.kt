package com.diploma.stats.global.utils.helper

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class ExecHelper : Executor {
    private val mHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mHandler.post(command)
    }
}
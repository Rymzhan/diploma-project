package com.thousand.bosch.app

import android.app.Application
import android.content.ContextWrapper
import com.thousand.bosch.global.di.appModule
import com.pixplicity.easyprefs.library.Prefs
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import io.socket.client.Socket


private lateinit var mSocket: Socket
private val EVENT_CONNECT = Socket.EVENT_CONNECT
private val EVENT_DISCONNECT = Socket.EVENT_DISCONNECT
private val EVENT_CONNECT_ERROR = Socket.EVENT_CONNECT_ERROR
private val EVENT_CONNECT_TIMEOUT = Socket.EVENT_CONNECT_TIMEOUT


class BoschApp : Application() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { throwable -> }

        startKoin {
            androidContext(this@BoschApp)
            modules(appModule)
        }

        initPrefs()
        Timber.plant(Timber.DebugTree())
    }

    private fun initPrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}
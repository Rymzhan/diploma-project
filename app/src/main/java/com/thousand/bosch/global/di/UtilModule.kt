package com.thousand.bosch.global.di

import com.thousand.bosch.global.functional.NetworkHandler
import com.thousand.bosch.global.system.AppSchedulers
import com.thousand.bosch.global.system.ResourceManager
import com.thousand.bosch.global.system.SchedulersProvider
import com.thousand.bosch.global.utils.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single { AppSchedulers() as SchedulersProvider }
    single { ResourceManager(androidContext()) }
    single { ErrorHandler(get()) }
    single { NetworkHandler(androidContext()) }
}
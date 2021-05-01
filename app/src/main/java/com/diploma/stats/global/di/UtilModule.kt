package com.diploma.stats.global.di

import com.diploma.stats.global.functional.NetworkHandler
import com.diploma.stats.global.system.AppSchedulers
import com.diploma.stats.global.system.ResourceManager
import com.diploma.stats.global.system.SchedulersProvider
import com.diploma.stats.global.utils.ErrorHandler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single { AppSchedulers() as SchedulersProvider }
    single { ResourceManager(androidContext()) }
    single { ErrorHandler(get()) }
    single { NetworkHandler(androidContext()) }
}
package com.diploma.stats.global.di

import com.diploma.stats.views.auth.di.authModule
import com.diploma.stats.views.auth.di.interactorRepositoryModule


val appModule = listOf(
    networkModule,
    utilModule,
    authModule,
    interactorRepositoryModule
)
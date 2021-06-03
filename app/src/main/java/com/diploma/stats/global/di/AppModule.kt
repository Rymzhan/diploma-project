package com.diploma.stats.global.di

import com.diploma.stats.views.scope.di.authModule
import com.diploma.stats.views.scope.di.interactorRepositoryModule


val appModule = listOf(
    networkModule,
    utilModule,
    authModule,
    interactorRepositoryModule
)
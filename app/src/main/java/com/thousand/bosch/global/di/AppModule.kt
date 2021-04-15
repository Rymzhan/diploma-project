package com.thousand.bosch.global.di

import com.thousand.bosch.views.auth.di.authModule
import com.thousand.bosch.views.auth.di.interactorRepositoryModule


val appModule = listOf(
    networkModule,
    utilModule,
    authModule,
    interactorRepositoryModule
)
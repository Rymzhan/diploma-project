package com.thousand.bosch.views.auth.di

import com.thousand.bosch.views.auth.interactors.UserInteractor
import com.thousand.bosch.views.auth.repositories.UserRepo
import com.thousand.bosch.views.auth.repositories.UserRepoImpl
import org.koin.dsl.module

val interactorRepositoryModule = module {
    single<UserRepo> { UserRepoImpl(get()) }
    single { UserInteractor(get(), get()) }
}
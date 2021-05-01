package com.diploma.stats.views.auth.di

import com.diploma.stats.views.auth.interactors.UserInteractor
import com.diploma.stats.views.auth.repositories.UserRepo
import com.diploma.stats.views.auth.repositories.UserRepoImpl
import org.koin.dsl.module

val interactorRepositoryModule = module {
    single<UserRepo> { UserRepoImpl(get()) }
    single { UserInteractor(get(), get()) }
}
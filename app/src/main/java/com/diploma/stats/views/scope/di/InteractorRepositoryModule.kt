package com.diploma.stats.views.scope.di

import com.diploma.stats.views.scope.interactors.UserInteractor
import com.diploma.stats.views.scope.repositories.UserRepo
import com.diploma.stats.views.scope.repositories.UserRepoImpl
import org.koin.dsl.module

val interactorRepositoryModule = module {
    single<UserRepo> { UserRepoImpl(get()) }
    single { UserInteractor(get(), get()) }
}
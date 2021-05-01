package com.diploma.stats.views.main.presentation.game.results.friend

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class GameFriendPresenter(private val userInteractor: UserInteractor) : BasePresenter<GameFriendView>(){

}
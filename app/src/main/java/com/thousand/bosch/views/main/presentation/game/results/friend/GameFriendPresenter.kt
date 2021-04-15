package com.thousand.bosch.views.main.presentation.game.results.friend

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class GameFriendPresenter(private val userInteractor: UserInteractor) : BasePresenter<GameFriendView>(){

}
package com.thousand.bosch.views.main.presentation.game.results.main.friends

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class FriendsTopListPresenter(private val userInteractor: UserInteractor): BasePresenter<FriendsTopListView>() {
    fun getTopFriends(){
        userInteractor.getTopFriends().subscribe({
                viewState.setView(it.data)
        },{
            it.printStackTrace()
        }).connect()
    }
}
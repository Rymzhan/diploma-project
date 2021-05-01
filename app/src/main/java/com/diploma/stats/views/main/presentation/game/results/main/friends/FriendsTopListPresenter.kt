package com.diploma.stats.views.main.presentation.game.results.main.friends

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

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
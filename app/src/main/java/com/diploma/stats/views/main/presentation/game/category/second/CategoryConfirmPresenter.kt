package com.diploma.stats.views.main.presentation.game.category.second

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class CategoryConfirmPresenter(val userInteractor: UserInteractor) :
    BasePresenter<CategoryConfirmView>() {
    val TAG = "CategoryConfirmPresenter"

    fun startRound(gameId: Int, categoryId: Int) {
        userInteractor.startRound(id = gameId, categoryId = categoryId).subscribe({
            if (it.isSuccessful) {
                val response = it.body()!!
                    viewState.startRound(response)
               } else {
                viewState.showError()
            }

        }, {
            it.printStackTrace()
        }).connect()
    }

}
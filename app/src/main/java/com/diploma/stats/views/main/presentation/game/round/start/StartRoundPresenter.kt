package com.diploma.stats.views.main.presentation.game.round.start

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class StartRoundPresenter(val userInteractor: UserInteractor) : BasePresenter<StartRoundView>() {

    fun startRound(gameId: Int, categoryId: Int) {
        userInteractor.startRound(id = gameId, categoryId = categoryId).subscribe({
            if (it.isSuccessful) {
                val response = it.body()!!
                viewState.startRound(
                    response.data.id,
                    response.data.questions[0],
                    response.data.questions[1],
                    response.data.questions[2]
                )
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun answerToRound(
        roundId: Int,
        firstQuestion: Int,
        firstAnswer: Int,
        secondQuestion: Int,
        secondAnswer: Int,
        thirdQuestion: Int,
        thirdAnswer: Int
    ) {
        userInteractor.answerToRound(
            roundId,
            firstQuestion,
            firstAnswer,
            secondQuestion,
            secondAnswer,
            thirdQuestion,
            thirdAnswer
        ).subscribe({
            if (it.isSuccessful) {
                viewState.openResultsPage()
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun loseRound(id: Int) {
        userInteractor.loseRound(id).subscribe({
            if (it.isSuccessful) {
                viewState.openResultsPage()
            }
        }, {
            viewState.showError()
            it.printStackTrace()
        }).connect()
    }
}
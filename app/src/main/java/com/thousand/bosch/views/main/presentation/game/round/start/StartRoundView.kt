package com.thousand.bosch.views.main.presentation.game.round.start

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.game.round.start.Question

@StateStrategyType(OneExecutionStateStrategy::class)
interface StartRoundView : BaseMvpView{
    fun startRound(id: Int, firstQuestion: Question, secondQuestion: Question, thirdQuestion: Question)
    fun openResultsPage()
    fun showError()
}
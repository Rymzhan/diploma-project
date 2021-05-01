package com.diploma.stats.views.main.presentation.game.round.start

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.game.round.start.Question

@StateStrategyType(OneExecutionStateStrategy::class)
interface StartRoundView : BaseMvpView{
    fun startRound(id: Int, firstQuestion: Question, secondQuestion: Question, thirdQuestion: Question)
    fun openResultsPage()
    fun showError()
}
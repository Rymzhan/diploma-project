package com.diploma.stats.views.main.presentation.game.category.second

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.game.round.start.RoundStartModel

@StateStrategyType(OneExecutionStateStrategy::class)
interface CategoryConfirmView : BaseMvpView {
    fun startRound(obj: RoundStartModel)
    fun showError()
}
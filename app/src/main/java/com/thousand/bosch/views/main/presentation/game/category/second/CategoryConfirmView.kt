package com.thousand.bosch.views.main.presentation.game.category.second

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.game.round.start.RoundStartModel

@StateStrategyType(OneExecutionStateStrategy::class)
interface CategoryConfirmView : BaseMvpView {
    fun startRound(obj: RoundStartModel)
    fun showError()
}
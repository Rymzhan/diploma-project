package com.diploma.stats.views.auth.presenters.restore.second

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RestoreSecondView: BaseMvpView {
    fun goToThird(resetToken: String)
    fun showError()
    fun showMessage()
}
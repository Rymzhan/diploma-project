package com.diploma.stats.views.auth.presenters.restore.third

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RestoreThirdView : BaseMvpView{
    fun openLoginFrag()
}
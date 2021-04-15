package com.thousand.bosch.views.auth.presenters.restore.third

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RestoreThirdView : BaseMvpView{
    fun openLoginFrag()
}
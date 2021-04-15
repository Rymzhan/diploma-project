package com.thousand.bosch.views.auth.presenters.registration.first

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RegistrationView: BaseMvpView {
    fun goToSecond(responsePhone: String)
    fun showError()
}
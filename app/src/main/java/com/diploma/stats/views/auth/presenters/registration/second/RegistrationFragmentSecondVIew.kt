package com.diploma.stats.views.auth.presenters.registration.second

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface RegistrationFragmentSecondVIew:BaseMvpView {
    fun openThird(regToken: String)
    fun showError()
    fun showMessage()
}
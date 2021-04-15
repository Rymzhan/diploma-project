package com.thousand.bosch.views.auth.presenters.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface LoginView:MvpView{
    fun openProfileFrag()
    fun showError(message: String)
}
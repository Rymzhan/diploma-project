package com.thousand.bosch.views.auth.presenters.restore.first

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface RestoreFragmentFirstView : MvpView {
    fun openSecondFragment(responsePhone: String)
    fun showError()
}
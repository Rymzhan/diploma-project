package com.thousand.bosch.views.main.presentation.profile.block_user

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.friends.DataX

@StateStrategyType(OneExecutionStateStrategy::class)
interface BlockUserView: BaseMvpView{
    fun bindList(friendsList: List<DataX>)
    fun showPD(show: Boolean)
    fun goToSettings()
}
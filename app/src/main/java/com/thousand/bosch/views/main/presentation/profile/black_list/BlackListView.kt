package com.thousand.bosch.views.main.presentation.profile.black_list

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.friends.DataX

@StateStrategyType(OneExecutionStateStrategy::class)
interface BlackListView: BaseMvpView {
    fun bindList(blackList: List<DataX>)
    fun showPD(show: Boolean)
}
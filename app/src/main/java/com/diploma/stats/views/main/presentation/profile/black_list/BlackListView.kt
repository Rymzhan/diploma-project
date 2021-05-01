package com.diploma.stats.views.main.presentation.profile.black_list

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.friends.DataX

@StateStrategyType(OneExecutionStateStrategy::class)
interface BlackListView: BaseMvpView {
    fun bindList(blackList: List<DataX>)
    fun showPD(show: Boolean)
}
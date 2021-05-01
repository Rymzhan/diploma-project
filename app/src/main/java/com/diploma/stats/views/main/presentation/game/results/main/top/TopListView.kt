package com.diploma.stats.views.main.presentation.game.results.main.top

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.model.list.top.Data

@StateStrategyType(OneExecutionStateStrategy::class)
interface TopListView: MvpView {
    fun setView(list: List<Data>)
}
package com.diploma.stats.views.main.presentation.help

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.model.web_view.Data

@StateStrategyType(OneExecutionStateStrategy::class)
interface HelpView : MvpView{
    fun bindWebView(data: List<Data>)
}
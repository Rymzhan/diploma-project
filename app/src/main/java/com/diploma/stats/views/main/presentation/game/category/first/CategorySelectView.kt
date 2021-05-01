package com.diploma.stats.views.main.presentation.game.category.first

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.game.categories.RandomCategories

@StateStrategyType(OneExecutionStateStrategy::class)
interface CategorySelectView: BaseMvpView{
    fun setCategories(response: RandomCategories)
}
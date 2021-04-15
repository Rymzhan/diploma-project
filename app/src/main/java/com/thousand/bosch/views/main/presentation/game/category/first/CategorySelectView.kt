package com.thousand.bosch.views.main.presentation.game.category.first

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.game.categories.RandomCategories

@StateStrategyType(OneExecutionStateStrategy::class)
interface CategorySelectView: BaseMvpView{
    fun setCategories(response: RandomCategories)
}
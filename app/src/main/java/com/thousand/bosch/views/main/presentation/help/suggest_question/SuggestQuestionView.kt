package com.thousand.bosch.views.main.presentation.help.suggest_question

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.model.main.game.categories.RandomCategories

@StateStrategyType(OneExecutionStateStrategy::class)
interface SuggestQuestionView : MvpView{
    fun bindCategories(list: RandomCategories?)
    fun openProfileFrag()
}
package com.diploma.stats.views.main.presentation.stats.student.city

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.department.dep_list.StudentCityResult

@StateStrategyType(OneExecutionStateStrategy::class)
interface StatsByCityView : BaseMvpView {
    fun showPD(show: Boolean)
    fun bindRecycler(userList: List<StudentCityResult>)
}
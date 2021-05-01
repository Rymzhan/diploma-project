package com.diploma.stats.views.main.presentation.stats.department.course

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.department.dep_list.CourseResult

@StateStrategyType(OneExecutionStateStrategy::class)
interface StatsByCourseView : BaseMvpView {
    fun showPD(show: Boolean)
    fun bindRecycler(userList: List<CourseResult>)
}
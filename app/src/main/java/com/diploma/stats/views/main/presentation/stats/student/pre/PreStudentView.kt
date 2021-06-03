package com.diploma.stats.views.main.presentation.stats.student.pre

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.department.dep_list.City
import com.diploma.stats.model.department.dep_list.StudentResponse

@StateStrategyType(OneExecutionStateStrategy::class)
interface PreStudentView : BaseMvpView{
    fun bindCitiesList(cities: MutableList<City>?)
    fun showEmptyResult()
    fun bindToNextPage(studentResponse: StudentResponse)
    fun showCorellation(response: Double?)
    fun showError(message: String?)

}
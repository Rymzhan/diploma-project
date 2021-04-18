package com.thousand.bosch.views.main.presentation.stats.student.pre

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.department.dep_list.City
import com.thousand.bosch.model.department.dep_list.StudentResponse

@StateStrategyType(OneExecutionStateStrategy::class)
interface PreStudentView : BaseMvpView{
    fun bindCitiesList(cities: MutableList<City>?)
    fun showEmptyResult()
    fun bindToNextPage(studentResponse: StudentResponse)

}
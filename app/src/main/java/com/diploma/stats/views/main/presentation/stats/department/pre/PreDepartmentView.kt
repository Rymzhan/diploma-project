package com.diploma.stats.views.main.presentation.stats.department.pre

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.department.dep_list.Course
import com.diploma.stats.model.department.dep_list.Department
import com.diploma.stats.model.department.dep_list.Group

@StateStrategyType(OneExecutionStateStrategy::class)
interface PreDepartmentView : BaseMvpView{

    fun bindDepartment(response: MutableList<Department>?)
    fun bindCourses(response: MutableList<Course>?)
    fun bindGroup(response: MutableList<Group>?)

}
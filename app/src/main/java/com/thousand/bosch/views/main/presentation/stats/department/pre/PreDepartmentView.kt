package com.thousand.bosch.views.main.presentation.stats.department.pre

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.department.dep_list.Course
import com.thousand.bosch.model.department.dep_list.Department
import com.thousand.bosch.model.department.dep_list.Group

@StateStrategyType(OneExecutionStateStrategy::class)
interface PreDepartmentView : BaseMvpView{

    fun bindDepartment(response: MutableList<Department>?)
    fun bindGroup(response: MutableList<Group>?)
    fun bindCourses(response: MutableList<Course>?)

}
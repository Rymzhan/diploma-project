package com.diploma.stats.views.main.presentation.stats.department.post

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.department.response.DepartmentResponse

@StateStrategyType(OneExecutionStateStrategy::class)
interface PostDepartmentView : BaseMvpView {

    fun bindResult(departmentResponse: DepartmentResponse)
    fun bindEmptyView()
}
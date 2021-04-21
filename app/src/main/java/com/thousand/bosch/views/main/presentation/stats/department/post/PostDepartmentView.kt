package com.thousand.bosch.views.main.presentation.stats.department.post

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.department.response.DepartmentResponse

@StateStrategyType(OneExecutionStateStrategy::class)
interface PostDepartmentView : BaseMvpView {

    fun bindResult(departmentResponse: DepartmentResponse)
    fun bindEmptyView()
}
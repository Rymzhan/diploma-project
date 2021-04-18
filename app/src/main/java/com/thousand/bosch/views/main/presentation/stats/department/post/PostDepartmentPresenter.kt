package com.thousand.bosch.views.main.presentation.stats.department.post

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor
import com.thousand.bosch.views.main.presentation.stats.department.pre.PreDepartmentView

@InjectViewState
class PostDepartmentPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<PostDepartmentView>() {

    fun getCalcByGroup(
        group_id: Int,
        course_id: Int
    ){
        userInteractor.getCalcByGroup(group_id,course_id).subscribe({response->
            viewState.bindResult(response[0])
        },{t->
            t.printStackTrace()
        }).connect()
    }

}
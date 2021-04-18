package com.thousand.bosch.views.main.presentation.stats.department.pre

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class PreDepartmentPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<PreDepartmentView>() {

    fun getDepartmentList(){
        userInteractor.departmentList().subscribe({response->
            viewState.bindDepartment(response)
        },{t->
            t.printStackTrace()
        }).connect()
    }

    fun getGroupList(departmentId: Int){
        userInteractor.groupList(departmentId).subscribe({response->
            viewState.bindGroup(response)
        },{t->
            t.printStackTrace()
        }).connect()
    }

    fun getCoursesList(department_id: Int) {
        userInteractor.getCourses(department_id).subscribe({response->
            viewState.bindCourses(response)
        },{t->
            t.printStackTrace()
        }).connect()
    }

}
package com.diploma.stats.views.main.presentation.stats.department.post

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.scope.interactors.UserInteractor

@InjectViewState
class PostDepartmentPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<PostDepartmentView>() {

    fun getCalcByGroup(
        group_id: Int?,
        course_id: Int?,
        department_id: Int?
    ){
        userInteractor.getCalcByGroup(group_id,course_id,department_id).subscribe({response->
            Log.i("responseCheck", response.toString())
            if(response.isEmpty()){
                viewState.bindEmptyView()
            }else{
                viewState.bindResult(response[0])
            }
        },{t->
            t.printStackTrace()
        }).connect()
    }

}
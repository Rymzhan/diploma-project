package com.thousand.bosch.views.main.presentation.stats.department.post

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor
import com.thousand.bosch.views.main.presentation.stats.department.pre.PreDepartmentView

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
package com.thousand.bosch.views.main.presentation.stats.student.pre

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class PreStudentPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<PreStudentView>() {

    fun getCitiesList() {
        userInteractor.citiesList().subscribe({ cities ->
            viewState.bindCitiesList(cities)
        }, { t ->
            t.printStackTrace()
        }).connect()
    }

    fun getStudentResultByIIN(iin: Long, city_id: Int?, last_name: String?, first_name: String?) {
        userInteractor.getStudentResult(iin, city_id, last_name, first_name).subscribe({ response ->
            if(response.isEmpty()){
                viewState.showEmptyResult()
            }else{
                viewState.bindToNextPage(response[0])
            }
        }, { t ->
            t.printStackTrace()
        }).connect()
    }

    fun getStudentResult(iin: Long?, city_id: Int?, last_name: String?, first_name: String?) {
        userInteractor.getStudentResult(iin, city_id, last_name, first_name).subscribe({ response ->
            if(response.isEmpty()){
                viewState.showEmptyResult()
            }else{
                viewState.bindToNextPage(response[0])
            }
        }, { t ->
            t.printStackTrace()
        }).connect()
    }
}
package com.diploma.stats.views.main.presentation.stats.student.pre

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.scope.interactors.UserInteractor

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
            viewState.showEmptyResult()
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
            viewState.showEmptyResult()
            t.printStackTrace()
        }).connect()
    }

    fun getCorellation() {
        userInteractor.getCorellation().subscribe({response->
            viewState.showCorellation(response.toDouble())
        },{t->
            viewState.showError(t.toString())
            t.printStackTrace()
        }).connect()
    }
}
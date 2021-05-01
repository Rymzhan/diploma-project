package com.diploma.stats.views.main.presentation.help.suggest_question

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.model.main.game.categories.RandomCategories
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class SuggestQuestionPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<SuggestQuestionView>() {
    private val TAG = "SuggestQuestionPresenter"

    fun getCategoryList() {
        userInteractor.categoriesRandom().subscribe({
            if (it.isSuccessful) {
                val list:RandomCategories? = it.body()
                viewState.bindCategories(list)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun suggestQuestion(email: String, id: Int, desc: String){
        userInteractor.suggestQuestion(email,id,desc).subscribe({
            if(it.isSuccessful){
                viewState.openProfileFrag()
            }
        },{
            it.printStackTrace()
        }).connect()
    }
}
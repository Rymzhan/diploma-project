package com.diploma.stats.views.main.presentation.game.category.first

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.model.main.game.categories.RandomCategories
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class CategorySelectPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<CategorySelectView>() {
    val TAG = "CategorySelectPresenter"

    fun randomCategories() {
        userInteractor.categoriesRandom().subscribe({
            if(it.isSuccessful){
                val response: RandomCategories = it.body()!!
                viewState.setCategories(response)
            }
        }, {

        }).connect()
    }
}
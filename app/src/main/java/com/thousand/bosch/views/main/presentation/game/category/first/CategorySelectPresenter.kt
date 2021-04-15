package com.thousand.bosch.views.main.presentation.game.category.first

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.model.main.game.categories.RandomCategories
import com.thousand.bosch.views.auth.interactors.UserInteractor

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
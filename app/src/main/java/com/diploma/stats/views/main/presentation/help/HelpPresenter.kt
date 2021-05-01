package com.diploma.stats.views.main.presentation.help

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class HelpPresenter(private val userInteractor: UserInteractor) : BasePresenter<HelpView>() {
    val TAG = "HelpPresenter"

    fun getWebView() {
        userInteractor.getWebView()
            .subscribe({
                if (it.success) {
                    viewState.bindWebView(it.data)
                }
            }, {
                it.printStackTrace()
            }).connect()
    }
}
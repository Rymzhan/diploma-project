package com.thousand.bosch.views.main.presentation.help

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

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
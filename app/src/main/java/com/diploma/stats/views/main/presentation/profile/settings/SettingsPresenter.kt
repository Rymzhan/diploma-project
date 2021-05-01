package com.diploma.stats.views.main.presentation.profile.settings

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.service.ApiModelHelper
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class SettingsPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<SettingsView>() {
    val TAG = "SettingsPresenter"

    fun getUserInfo() {
        userInteractor.user_info().subscribe({
            if (it.isSuccessful) {
                val user: User? = ApiModelHelper.getObject(it.body()!!, User::class.java)
                if (user != null) {
                    viewState.bindUserInfo(user)
                }
            }else{
                viewState.logOut()
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun log_out() = userInteractor.log_out().subscribe({
        val code: Int = it.statusCode
        if (code == 200) {
            viewState.logOut()
        }
    }, {
        it.printStackTrace()
    }).connect()

    fun deleteAccount() = userInteractor.deleteAccount().subscribe({
        viewState.logOut()
    }, {
        it.printStackTrace()
    }).connect()

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
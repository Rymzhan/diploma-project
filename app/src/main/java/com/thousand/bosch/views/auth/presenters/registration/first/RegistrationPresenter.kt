package com.thousand.bosch.views.auth.presenters.registration.first

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class RegistrationPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<RegistrationView>() {
    val TAG = "LoginPresenter"

    @SuppressLint("CheckResult")
    fun register(phone: String) {
        userInteractor.register(phone).subscribe({
            if (it.code() == 200) {
                val responsePhone: String? = it.body()!!.data.phone
                if (responsePhone != null) {
                    viewState.goToSecond(responsePhone)
                }
            } else {
                viewState.showError()
            }
        }, {
            it.printStackTrace()
        }).connect()
    }
}
package com.diploma.stats.views.auth.presenters.registration.second

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class RegistrationFragmentSecondPresenter(val userInteractor: UserInteractor) :
    BasePresenter<RegistrationFragmentSecondVIew>() {
    val TAG = "RegSecondPresenter"

    @SuppressLint("CheckResult")
    fun confirm_reg(phone: String, code: String) =
        userInteractor.confirm_registr(phone = phone, code = code).subscribe({
            val regToken: String? = it.data.registration_token
            if (regToken != null) {
                viewState.openThird(regToken)
            }
        }, {
            viewState.showError()
            Log.d(TAG, it.toString())
        }).connect()

    @SuppressLint("CheckResult")
    fun register(phone: String) {
        userInteractor.register(phone).subscribe({
            if (it.code() == 200) {
                val responsePhone: String? = it.body()!!.data.phone
                if (responsePhone != null) {
                    viewState.showMessage()
                }
            } else {
                viewState.showError()
            }
        }, {
            it.printStackTrace()
        }).connect()
    }
}
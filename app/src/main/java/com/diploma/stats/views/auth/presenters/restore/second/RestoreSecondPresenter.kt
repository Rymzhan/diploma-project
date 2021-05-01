package com.diploma.stats.views.auth.presenters.restore.second

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class RestoreSecondPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<RestoreSecondView>() {
    val TAG = "RestoreSecondPresenter"
    fun reset_confirm(phone: String, code: String) =
        userInteractor.reset_confirm(phone = phone, code = code).subscribe({
            val resetToken: String? = it.data.reset_token
            if (resetToken != null) {
                viewState.goToThird(resetToken)
            }
        }, {
            viewState.showError()
            it.printStackTrace()
            Log.d(TAG, it.toString())
        }).connect()

    fun reset_request(phone: String) = userInteractor.reset_request(phone = phone).subscribe({
        val responsePhone: String? = it.data.phone
        if (responsePhone != null) {
            viewState.showMessage()
        }
    }, {
        viewState.showError()
        it.printStackTrace()
        Log.d(TAG, it.toString())
    }).connect()
}
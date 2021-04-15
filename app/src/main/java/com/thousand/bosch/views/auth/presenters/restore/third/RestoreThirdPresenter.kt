package com.thousand.bosch.views.auth.presenters.restore.third

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class RestoreThirdPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<RestoreThirdView>() {
    val TAG = "RestoreThirdPresenter"
    fun restore_new_password(phone: String, reset_token: String, new_password: String) =
        userInteractor.reset_new_password(
            phone = phone,
            reset_token = reset_token,
            new_password = new_password
        ).subscribe({
            val message: Int = it.statusCode
            if (message == 200) {

                viewState.openLoginFrag()
            }
        }, {
            it.printStackTrace()
            Log.d(TAG, it.toString())
        }).connect()
}
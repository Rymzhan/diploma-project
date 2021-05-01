package com.diploma.stats.views.auth.presenters.restore.first

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class RestoreFragmentFirstPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<RestoreFragmentFirstView>() {
    val TAG = "RestoreFirstPresenter"
    fun reset_request(phone: String) = userInteractor.reset_request(phone = phone).subscribe({
        val responsePhone: String? = it.data.phone
        if(responsePhone!=null){
            viewState.openSecondFragment(responsePhone)
        }
    }, {
        viewState.showError()
        it.printStackTrace()
        Log.d(TAG, it.toString())
    }).connect()
}
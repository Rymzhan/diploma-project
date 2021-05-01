package com.diploma.stats.views.main.presentation.activity

import android.util.Log
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter

@InjectViewState
class MainActivityPresenter: BasePresenter<MainActivityView>() {

    val TAG = "MainActivityPresenter"

    fun onFragmentAttach(fragment: Fragment){
        Log.i(TAG, fragment.tag)
    }

    fun onFragmentDetach(fragment: Fragment){

    }

}
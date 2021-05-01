package com.diploma.stats.global.base

import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.MvpView

interface BaseMvpView: MvpView{
    fun closeKeyboard(activity: FragmentActivity?)
}
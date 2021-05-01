package com.diploma.stats.views.main.presentation.profile.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.service.ApiModelHelper
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.model.main.profile.turns.*
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class ProfilePresenter(private val userInteractor: UserInteractor) : BasePresenter<ProfileView>() {
    val TAG = "ProfilePresenter"

    var finished: MutableList<Finished> = mutableListOf()
    var myTurn: MutableList<MyTurn> = mutableListOf()
    var waiting: MutableList<Waiting> = mutableListOf()

    fun onFirstInit() {
        getUserInfo()
        getGameList()
        //getMessageSocketListener()
    }

/*    private fun getMessageSocketListener() {
        userInteractor.getMessageSocket()
            .subscribe(
                {
                    Timber.d("TEXT: $it")
                    val socketResp = it
                    if(socketResp.event=="user.updated"){
                        val user: User = ApiModelHelper.getObject(it.data, User::class.java)
                        viewState.bindUserInfo(user)
                    }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }*/

    fun getUserInfo() {
        userInteractor.user_info().subscribe({
            if (it.isSuccessful) {
                val user: User? = ApiModelHelper.getObject(it.body()!!, User::class.java)
                if (user != null) {
                    viewState.bindUserInfo(user)
                }
            } else {
                viewState.logOut()
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getGameList() {
        userInteractor.getGamesList().subscribe({
            if (it.isSuccessful) {
                Log.i("getGameList", "preesenter")
                viewState.bindTurnList(
                    it.body()!!.data.finished,
                    it.body()!!.data.myTurn,
                    it.body()!!.data.waiting
                )
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

}
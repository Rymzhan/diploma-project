package com.thousand.bosch.views.main.presentation.profile.details

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.global.service.ApiModelHelper
import com.thousand.bosch.model.auth.login.User
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.profile.statistics.StatisticsModel
import com.thousand.bosch.views.auth.interactors.UserInteractor
import java.lang.Exception

@InjectViewState
class ProfileDetailsPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<ProfileDetailsView>() {
    val TAG = "ProfileDetailsPresenter"

    fun getUserInfo() {
        userInteractor.user_info().subscribe({
            if (it.isSuccessful) {
                val user: User? = ApiModelHelper.getObject(it.body()!!, User::class.java)
                if (user != null) {
                    viewState.bindUserInfo(user)
                }
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getUserStats() {
        userInteractor.myStats().subscribe({
            if (it.isSuccessful) {
                val response: StatisticsModel = it.body()!!
                viewState.bindStats(response.data.stats, response.data.category_stats)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun myFriends() {
        userInteractor.list_friends().subscribe({
            val tempList: MutableList<DataX> = mutableListOf()
            val friendsList: List<DataX> = it.data.data
            if (friendsList.isNotEmpty()) {
                for (x in 0 until 3) {
                    try {
                        tempList.add(friendsList.elementAt(x))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            viewState.bindFriendsRecycler(tempList.sortedBy { it1 -> it1.first_name })
        }, {
            it.printStackTrace()
        }).connect()
    }

}
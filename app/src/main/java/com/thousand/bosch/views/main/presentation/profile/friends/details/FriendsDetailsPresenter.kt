package com.thousand.bosch.views.main.presentation.profile.friends.details

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.global.service.ApiModelHelper
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.profile.statistics.StatisticsModel
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class FriendsDetailsPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<FriendsDetailsView>() {

    val TAG = "FriendsDetailsPresenter"

    fun getFriendInfo(id: Int) {
        userInteractor.friend_info(id = id).subscribe({
            if (it.isSuccessful) {
                val friend: DataX = ApiModelHelper.getObject(it.body()!!, DataX::class.java)
                viewState.bindFriendInfo(friend)
            } else {
                getUserById(id)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun add_friend(id: Int) = userInteractor.add_friend(id = id).subscribe({
        val user: DataX? = ApiModelHelper.getObject(it, DataX::class.java)
        if (user != null) {
            getFriendInfo(id)
        }
    }, {
        it.printStackTrace()
    }).connect()

    fun delete_user(id: Int) = userInteractor.delete_user(id).subscribe({
        val friend: DataX? = ApiModelHelper.getObject(it, DataX::class.java)
        if (friend != null) {
            viewState.bindFriendInfoAfterDelete(friend)
        }
    }, {
        it.printStackTrace()
    }).connect()

    fun blockUser(id: Int) {
        val list: MutableList<Int> = mutableListOf()
        list.clear()
        list.add(id)
        userInteractor.blockList(list).subscribe({
            if (it.isSuccessful) {
                getUserById(id)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getUserById(id: Int) {
        userInteractor.userById(id).subscribe({
            if (it.isSuccessful) {
                val friend: DataX? = ApiModelHelper.getObject(it.body()!!, DataX::class.java)
                viewState.bindFriendInfo(friend!!)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun unBlock(id: Int) {
        val list: MutableList<Int> = mutableListOf()
        list.clear()
        list.add(id)
        userInteractor.removeList(list).subscribe({
            if (it.isSuccessful) {
                getUserById(id)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getUserStats(id: Int) = userInteractor.userStatistics(id).subscribe({
        if (it.isSuccessful) {
            val response: StatisticsModel = it.body()!!
            viewState.bindStats(response.data.stats, response.data.category_stats)
        }
    }, {
        it.printStackTrace()
    }).connect()

}
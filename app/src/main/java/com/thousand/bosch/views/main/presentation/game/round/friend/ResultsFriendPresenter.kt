package com.thousand.bosch.views.main.presentation.game.round.friend

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.global.service.ApiModelHelper
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class ResultsFriendPresenter(val userInteractor: UserInteractor): BasePresenter<ResultsFriendView>() {
    val TAG = "ResultsFriendPresenter"

    fun startNewGame(id: Int){
        userInteractor.newGameFriend(id).subscribe({
            if(it.isSuccessful){
                viewState.bindRecyclerView(it.body()!!)
            }else{
                viewState.showError(it.errorBody().toString())
            }
        },{
            it.printStackTrace()
        }).connect()
    }

    fun gameById(id: Int){
        userInteractor.gameById(id).subscribe({
            if(it.isSuccessful){
                viewState.bindRecyclerView(it.body()!!)
            }
        },{
            it.printStackTrace()
        }).connect()
    }

    fun loseGame(id: Int){
        userInteractor.loseGame(id).subscribe({
            if(it.isSuccessful){
                viewState.openProfileFrag()
            }
        },{
            it.printStackTrace()
        }).connect()
    }

    fun add_friend(id: Int) = userInteractor.add_friend(id = id).subscribe({
        val user: DataX? = ApiModelHelper.getObject(it, DataX::class.java)
        viewState?.addFriendResult("Пользователь добавлен в друзья")
    }, {
        it.printStackTrace()
    }).connect()

}
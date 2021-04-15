package com.thousand.bosch.views.main.presentation.game.results.main.top

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.model.list.top.Data
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class TopListPresenter(private val userInteractor: UserInteractor): BasePresenter<TopListView>() {

    fun getTopList(){
        userInteractor.getTop().subscribe({
            val resp = it.data
            resp.users?.toMutableList()?.let { it1 ->
                if (resp.currentUser?.position ?: 0 > 20){
                    it1.add(
                        Data(
                            first_name = resp.currentUser?.first_name ?: "",
                            last_name = resp.currentUser?.last_name ?: "",
                            login = resp.currentUser?.login ?: "",
                            rating = resp.currentUser?.rating ?: 0,
                            position = resp.currentUser?.position ?: 0,
                            image = resp.currentUser?.image,
                            in_blacklist = false,
                            in_friends = false,
                            scores = resp.currentUser?.scores ?: 0,
                            id = resp.currentUser?.id ?: 0
                        )
                    )
                }
                viewState.setView(it1)
            }
        },{
            it.printStackTrace()
        }).connect()
    }

}
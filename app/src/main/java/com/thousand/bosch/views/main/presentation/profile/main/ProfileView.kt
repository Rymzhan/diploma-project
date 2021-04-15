package com.thousand.bosch.views.main.presentation.profile.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.model.auth.login.User
import com.thousand.bosch.model.main.profile.turns.Finished
import com.thousand.bosch.model.main.profile.turns.MyTurn
import com.thousand.bosch.model.main.profile.turns.Waiting

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileView : MvpView {
    fun bindUserInfo(user: User)
    fun logOut()
    fun bindTurnList(finished: List<Finished>?, myTurn: List<MyTurn>?, waiting: List<Waiting>?)
}
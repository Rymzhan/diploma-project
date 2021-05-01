package com.diploma.stats.views.main.presentation.profile.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.model.main.profile.turns.Finished
import com.diploma.stats.model.main.profile.turns.MyTurn
import com.diploma.stats.model.main.profile.turns.Waiting

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileView : MvpView {
    fun bindUserInfo(user: User)
    fun logOut()
    fun bindTurnList(finished: List<Finished>?, myTurn: List<MyTurn>?, waiting: List<Waiting>?)
}
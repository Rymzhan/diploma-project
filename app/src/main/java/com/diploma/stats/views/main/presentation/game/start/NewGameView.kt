package com.diploma.stats.views.main.presentation.game.start

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.search.SearchUser

@StateStrategyType(OneExecutionStateStrategy::class)
interface NewGameView: BaseMvpView {
    fun bindFriendsRecycler(friendsList: List<DataX>)
    fun goToResultsPage(id: Int)
    fun showPD(show: Boolean)
    fun bindRecycler(userList: List<SearchUser>)
}
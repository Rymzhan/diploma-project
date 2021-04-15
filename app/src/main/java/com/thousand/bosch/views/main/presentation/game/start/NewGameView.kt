package com.thousand.bosch.views.main.presentation.game.start

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.search.SearchUser

@StateStrategyType(OneExecutionStateStrategy::class)
interface NewGameView: BaseMvpView {
    fun bindFriendsRecycler(friendsList: List<DataX>)
    fun goToResultsPage(id: Int)
    fun showPD(show: Boolean)
    fun bindRecycler(userList: List<SearchUser>)
}
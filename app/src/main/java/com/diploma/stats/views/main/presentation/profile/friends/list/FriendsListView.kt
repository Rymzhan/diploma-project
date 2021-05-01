package com.diploma.stats.views.main.presentation.profile.friends.list

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.search.SearchUser


@StateStrategyType(OneExecutionStateStrategy::class)
interface FriendsListView: BaseMvpView {
    fun bindRecycler(userList: List<SearchUser>)
    fun showPD(show: Boolean)
    fun bindFriendsRecycler(friendsList: List<DataX>)
}
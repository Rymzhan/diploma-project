package com.thousand.bosch.views.main.presentation.profile.friends.list

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.search.SearchUser


@StateStrategyType(OneExecutionStateStrategy::class)
interface FriendsListView: BaseMvpView {
    fun bindRecycler(userList: List<SearchUser>)
    fun showPD(show: Boolean)
    fun bindFriendsRecycler(friendsList: List<DataX>)
}
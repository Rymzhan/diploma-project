package com.thousand.bosch.views.main.presentation.profile.friends.details

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.profile.statistics.CategoryStat
import com.thousand.bosch.model.main.profile.statistics.Stats

@StateStrategyType(OneExecutionStateStrategy::class)
interface FriendsDetailsView : BaseMvpView{
    fun bindFriendInfo(friend: DataX)
    fun bindFriendInfoAfterDelete(friend: DataX)
    fun goToList()
    fun bindUserInfo()
    fun bindStats(stats: Stats, categoryStats: List<CategoryStat>)
}
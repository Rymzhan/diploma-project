package com.thousand.bosch.views.main.presentation.profile.details

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.auth.login.User
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.profile.statistics.CategoryStat
import com.thousand.bosch.model.main.profile.statistics.Stats

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileDetailsView : BaseMvpView{
    fun bindUserInfo(user: User)
    fun bindFriendsRecycler(friendsList: List<DataX>)
    fun bindStats(stats: Stats, categoryStats: List<CategoryStat>)
}
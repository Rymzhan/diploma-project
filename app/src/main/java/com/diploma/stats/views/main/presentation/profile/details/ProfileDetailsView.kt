package com.diploma.stats.views.main.presentation.profile.details

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.profile.statistics.CategoryStat
import com.diploma.stats.model.main.profile.statistics.Stats

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileDetailsView : BaseMvpView{
    fun bindUserInfo(user: User)
    fun bindFriendsRecycler(friendsList: List<DataX>)
    fun bindStats(stats: Stats, categoryStats: List<CategoryStat>)
}
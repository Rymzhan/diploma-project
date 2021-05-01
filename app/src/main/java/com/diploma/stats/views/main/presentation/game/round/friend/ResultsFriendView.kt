package com.diploma.stats.views.main.presentation.game.round.friend

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.main.game.start.main.MainGameResponse

@StateStrategyType(OneExecutionStateStrategy::class)
interface ResultsFriendView: BaseMvpView {
    fun bindRecyclerView(obj: MainGameResponse)
    fun openProfileFrag()
    fun showError(message: String)
    fun addFriendResult(message: String)
}
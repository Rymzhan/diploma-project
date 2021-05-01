package com.diploma.stats.views.main.presentation.profile.settings

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.model.web_view.Data

@StateStrategyType(OneExecutionStateStrategy::class)
interface SettingsView: MvpView{
    fun bindUserInfo(user: User)
    fun logOut()
    fun bindWebView(data: List<Data>)

}
package com.diploma.stats.views.main.presentation.profile.edit

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.list.Cities
import com.diploma.stats.model.list.Countries

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileEditView : BaseMvpView {
    fun openProfileFrag()
    fun showError(mes: String)
    fun bindCountry(countries: Countries)
    fun bindCity(cities: Cities)
}
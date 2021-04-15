package com.thousand.bosch.views.main.presentation.profile.edit

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.list.Cities
import com.thousand.bosch.model.list.Countries

@StateStrategyType(OneExecutionStateStrategy::class)
interface ProfileEditView : BaseMvpView {
    fun openProfileFrag()
    fun showError(mes: String)
    fun bindCountry(countries: Countries)
    fun bindCity(cities: Cities)
}
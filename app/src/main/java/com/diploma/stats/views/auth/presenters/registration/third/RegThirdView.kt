package com.diploma.stats.views.auth.presenters.registration.third

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.diploma.stats.global.base.BaseMvpView
import com.diploma.stats.model.list.Cities
import com.diploma.stats.model.list.Countries

@StateStrategyType(OneExecutionStateStrategy::class)
interface RegThirdView : BaseMvpView{
    fun goToProfile()
    fun showError(mes: String)
    fun bindCountries(countries: Countries)
    fun bindCities(cities: Cities)
}
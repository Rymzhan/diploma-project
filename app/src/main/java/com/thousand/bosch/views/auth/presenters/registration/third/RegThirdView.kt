package com.thousand.bosch.views.auth.presenters.registration.third

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.global.base.BaseMvpView
import com.thousand.bosch.model.list.Cities
import com.thousand.bosch.model.list.Countries

@StateStrategyType(OneExecutionStateStrategy::class)
interface RegThirdView : BaseMvpView{
    fun goToProfile()
    fun showError(mes: String)
    fun bindCountries(countries: Countries)
    fun bindCities(cities: Cities)
}
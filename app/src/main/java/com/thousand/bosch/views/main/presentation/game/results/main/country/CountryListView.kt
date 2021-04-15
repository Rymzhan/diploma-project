package com.thousand.bosch.views.main.presentation.game.results.main.country

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.thousand.bosch.model.list.Cities
import com.thousand.bosch.model.list.Countries
import com.thousand.bosch.model.list.top.Data

@StateStrategyType(OneExecutionStateStrategy::class)
interface CountryListView: MvpView {
    fun bindCountries(countries: Countries)
    fun bindCities(cities: Cities)
    fun setView(list: List<Data>)
}
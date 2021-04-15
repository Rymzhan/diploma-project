package com.thousand.bosch.views.main.presentation.game.results.main.country

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class CountryListPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<CountryListView>() {
    fun getCountries() {
        userInteractor.getCountries().subscribe({
            viewState.bindCountries(it)
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getCities(id: Int) {
        userInteractor.getCities(id).subscribe({
            viewState.bindCities(it)
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getTopByCountry(id: Int) {
        userInteractor.getTopByCountry(id).subscribe({
            viewState.setView(it.data)
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getTopByCity(countryId: Int, cityId: Int) {
        userInteractor.getTopByCity(country_id = countryId, city_id = cityId).subscribe({
            viewState.setView(it.data)
        }, {
            it.printStackTrace()
        }).connect()
    }
}
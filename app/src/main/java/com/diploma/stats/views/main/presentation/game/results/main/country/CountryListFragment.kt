package com.diploma.stats.views.main.presentation.game.results.main.country

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.model.list.Cities
import com.diploma.stats.model.list.Countries
import com.diploma.stats.model.list.top.Data
import com.diploma.stats.views.adapters.TopTwentyAdapter
import com.diploma.stats.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_country_list.*
import kotlinx.android.synthetic.main.fragment_registration_third.citySpinner
import kotlinx.android.synthetic.main.fragment_registration_third.countrySpinner
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class CountryListFragment : BaseFragment(), CountryListView {
    override val layoutRes: Int = R.layout.fragment_country_list

    private var countryId: Int? = null
    private var cityId: Int? = null

    private var workPlace: String = ""

    private var mainCountries: Countries? = null
    private var mainCities: Cities? = null

    private val firstList: MutableList<Int> = mutableListOf()
    private val secondList: MutableList<String> = mutableListOf()
    private val tempList1: MutableList<Int> = mutableListOf()
    private val tempList2: MutableList<String> = mutableListOf()

    @InjectPresenter
    lateinit var presenter: CountryListPresenter

    @ProvidePresenter
    fun providePresenter(): CountryListPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.COUNTRY_LIST_SCOPE,
            named(AuthScope.COUNTRY_LIST_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.COUNTRY_LIST_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgressDialog(true)
        presenter.getCountries()
    }

    override fun bindCountries(countries: Countries) {
        mainCountries = countries
        firstList.clear()
        secondList.clear()
        val spinner: Spinner = countrySpinner
        secondList.add("Выберите страну")
        for (i in countries.indices) {
            firstList.add(countries[i].id)
            secondList.add(countries[i].title)
        }

        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            secondList
        )
        spinner.adapter = adapter
        showProgressDialog(false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    countryId = firstList[position - 1]
                    showProgressDialog(true)
                    presenter.getTopByCountry(countryId!!)
                    presenter.getCities(countryId!!)
                }
            }
        }
    }

    override fun bindCities(cities: Cities) {
        mainCities = cities
        val spinner: Spinner = citySpinner
        tempList1.clear()
        tempList2.clear()
        tempList2.add("Выберите город")
        for (i in cities.indices) {
            tempList1.add(cities[i].id)
            tempList2.add(cities[i].title)
        }
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            tempList2
        )
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    showProgressDialog(true)
                    cityId = tempList1[position - 1]
                    presenter.getTopByCity(countryId!!, cityId!!)
                }
            }
        }
    }

    override fun setView(list: List<Data>) {
        val adapter = TopTwentyAdapter(requireContext(), requireActivity())
        adapter.pushAdapter(list)
        adapter.notifyDataSetChanged()
        val recyclerView = topListCountryRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        showProgressDialog(false)
    }


}
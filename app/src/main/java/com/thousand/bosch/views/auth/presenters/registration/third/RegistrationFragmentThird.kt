package com.thousand.bosch.views.auth.presenters.registration.third

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.thousand.bosch.R
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.model.list.Cities
import com.thousand.bosch.model.list.Countries
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_registration_third.*
import kotlinx.android.synthetic.main.fragment_registration_third.imageView
import kotlinx.android.synthetic.main.fragment_registration_third.imageView12
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception

class RegistrationFragmentThird() : BaseFragment(), RegThirdView {
    override val layoutRes: Int = R.layout.fragment_registration_third

    private var handler: Handler? = null

    private var workPlace: String = ""

    private var mainCountries: Countries? = null
    private var mainCities: Cities? = null

    private var countryId: Int? = null
    private var cityId: Int? = null

    private val firstList: MutableList<Int> = mutableListOf()
    private val secondList: MutableList<String> = mutableListOf()
    private val tempList1: MutableList<Int> = mutableListOf()
    private val tempList2: MutableList<String> = mutableListOf()

    @InjectPresenter
    lateinit var presenter: RegThirdPresenter

    @ProvidePresenter
    fun providePresenter(): RegThirdPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.CREATE_USER_SCOPE,
            named(AuthScope.CREATE_USER_SCOPE)
        )
            .get()
    }

    override fun onDestroy() {
        try {
            refreshData!!.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getKoin().getScopeOrNull(AuthScope.CREATE_USER_SCOPE)?.close()
        super.onDestroy()
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (start > 5) {
                imageView12.visibility = View.VISIBLE
                imageView.visibility = View.GONE
            }
        }
    }

    override fun setUp(savedInstanceState: Bundle?) {
        hideShow()
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }

        bindWorkplaceSpinner()

        presenter.getCountries()

        apply { registrationPasswordName.addTextChangedListener(textWatcher) }

        finishRegistrationBtn.setSafeOnClickListener {
            if (registrationLoginName.text.isEmpty()) {
                registrationLoginName.error = "Данное поле обязательно для заполнения!"
            }
            if (registrationName.text.isEmpty()) {
                registrationName.error = "Данное поле обязательно для заполнения!"
            }
            if (registrationSurName.text.isEmpty()) {
                registrationSurName.error = "Данное поле обязательно для заполнения!"
            }
            if (registrationOrgName.text.isEmpty()) {
                registrationOrgName.error = "Данное поле обязательно для заполнения!"
            }
            if (registrationPasswordName.text.length < 7) {
                imageView12.visibility = View.GONE
                imageView.visibility = View.GONE
                registrationPasswordName.error = "Пароль должен содержать свыше 6 символов!"
            }
            if (countryId == null || cityId == null) {
                Toast.makeText(context!!, "Выберите страну и город!", Toast.LENGTH_SHORT).show()
            } else {
                if (workPlace.length < 2) {
                    Toast.makeText(requireContext(), "Выберите место работы!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (registrationLoginName.text.isEmpty()) {
                        registrationLoginName.error = "Данное поле обязательно для заполнения!"
                    }
                    if (registrationName.text.isEmpty()) {
                        registrationName.error = "Данное поле обязательно для заполнения!"
                    }
                    if (registrationSurName.text.isEmpty()) {
                        registrationSurName.error = "Данное поле обязательно для заполнения!"
                    }
                    if (registrationOrgName.text.isEmpty()) {
                        registrationOrgName.error = "Данное поле обязательно для заполнения!"
                    }
                    if (registrationPasswordName.text.length < 7) {
                        imageView12.visibility = View.GONE
                        imageView.visibility = View.GONE
                        registrationPasswordName.error = "Пароль должен содержать свыше 6 символов!"
                    } else {
                        showProgress(context!!)
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                return@OnCompleteListener
                            }
                            presenter.create_user(
                                registrationLoginName.text.toString(),
                                registrationName.text.toString(),
                                registrationSurName.text.toString(),
                                registrationOrgName.text.toString(),
                                workPlace,
                                countryId!!,
                                cityId!!,
                                arguments?.getString(register_phone)!!,
                                arguments?.getString(register_token)!!,
                                registrationPasswordName.text.toString(),
                                null,
                                task.result!!
                            )
                        })
                    }
                }
            }
        }
    }

    private fun bindWorkplaceSpinner() {
        val spinner1: Spinner = workplaceSpinner
        val tempList: MutableList<String> = mutableListOf()
        tempList.add("Выберите место работы")
        tempList.add("Торгующая о.")
        tempList.add("Строительная к.")
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            tempList
        )
        spinner1.adapter = adapter

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    workPlace = tempList[position]
                } else if (position == 0) {
                    workPlace = ""
                }
            }
        }
    }

    private fun hideShow() {
        imageView12.setOnClickListener {
            registrationPasswordName.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            imageView12.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            registrationPasswordName.setSelection(registrationPasswordName.text.length)
        }

        imageView.setOnClickListener {
            registrationPasswordName.transformationMethod =
                PasswordTransformationMethod.getInstance()
            imageView12.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            registrationPasswordName.setSelection(registrationPasswordName.text.length)
        }
    }

    override fun goToProfile() {
        handler = Handler()
        handler!!.postDelayed({
            removeProgress()
        }, 300)
        showProgress(requireContext())
        activity?.supportFragmentManager?.replaceFragment(
            R.id.Container,
            ProfileFragment.newInstance(),
            ProfileFragment.TAG
        )
    }

    override fun showError(mes: String) {
        removeProgress()
        showMessage(mes, requireView())
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
                    cityId = tempList1[position - 1]
                }
            }
        }
    }

    companion object {
        const val register_token = "register_token"
        const val register_phone = "register_phone"
        val TAG = "RegistrationFragmentThird"
        var refreshData: (() -> Unit)? = null
        fun newInstance(
            loadData: () -> Unit,
            phone: String,
            regToken: String
        ): RegistrationFragmentThird {
            val fragment = RegistrationFragmentThird()
            refreshData = loadData
            val args = Bundle()
            args.putString(register_token, regToken)
            args.putString(register_phone, phone)
            fragment.arguments = args
            return fragment
        }
    }

}

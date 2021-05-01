package com.diploma.stats.views.auth.presenters.registration.first

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.diploma.stats.R
import com.diploma.stats.global.extension.addFragmentWithBackStack
import com.diploma.stats.global.extension.replaceFragment
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.auth.presenters.login.LoginFragment
import com.diploma.stats.views.auth.presenters.registration.second.RegistrationFragmentSecond
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.diploma.stats.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class RegistrationFragment() : BaseFragment(), RegistrationView {
    override val layoutRes: Int = R.layout.fragment_registration
    private var handler: Handler? = null
    override fun setUp(savedInstanceState: Bundle?) {
        onClick()
    }

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationPresenter {
        return getKoin().getOrCreateScope(AuthScope.REGISTER_SCOPE, named(AuthScope.REGISTER_SCOPE))
            .get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.REGISTER_SCOPE)?.close()
        super.onDestroy()
    }

    var phone = ""

    companion object {
        val TAG = "RegistrationFragment"
        fun newInstance(): RegistrationFragment = RegistrationFragment()
    }

    private fun onClick() {
        removeProgress()

        MaskedTextChangedListener.installOn(
            registrationNumber,
            getString(R.string.format_phone_number),
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    phone = if (extractedValue.length > 10) {
                        extractedValue
                    } else {
                        "+7"
                    }
                }
            }
        )

        registrationLoginBtn.setSafeOnClickListener {
            activity?.supportFragmentManager?.replaceFragment(
                R.id.Container,
                LoginFragment.newInstance(),
                LoginFragment.TAG
            )
        }
        registrationConfirmBtn.setSafeOnClickListener {
            if (phone.length < 10 || phone.isEmpty()) {
                Toast.makeText(context!!, "Неверный формат номера!", Toast.LENGTH_SHORT).show()
            } else {
                if (!checkBox.isChecked) {
                    Toast.makeText(context!!, "Подтвердите свое согласие!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    presenter.register(phone)
                }
            }
        }
    }

    override fun goToSecond(responsePhone: String) {
        showProgress(context!!)
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.registrationPage,
            RegistrationFragmentSecond.newInstance(responsePhone),
            RegistrationFragmentSecond.TAG
        )
    }

    override fun showError() {
        Toast.makeText(
            context!!,
            "Номер занят или время ожидания истекло, попробуйте позже!",
            Toast.LENGTH_SHORT
        ).show()
    }
}

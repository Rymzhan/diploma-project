package com.thousand.bosch.views.auth.presenters.restore.first

import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.thousand.bosch.R
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.auth.presenters.restore.second.RestoreFragmentSecond
import com.thousand.bosch.global.base.BaseFragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_restore_first.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class RestoreFragmentFirst() : BaseFragment(), RestoreFragmentFirstView {

    var phone = ""

    override val layoutRes: Int = R.layout.fragment_restore_first

    @InjectPresenter
    lateinit var presenter: RestoreFragmentFirstPresenter

    @ProvidePresenter
    fun providePresenter(): RestoreFragmentFirstPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.RESET_REQUEST_SCOPE,
            named(AuthScope.RESET_REQUEST_SCOPE)
        )
            .get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.RESET_REQUEST_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        removeProgress()
        onClick()
    }

    private fun onClick() {
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }

        MaskedTextChangedListener.installOn(
            restoreNumber,
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

        restoreConfirmBtn.setSafeOnClickListener {
            if (phone.length < 10 || phone.isEmpty()) {
                Toast.makeText(context!!, "Неверный формат номера!", Toast.LENGTH_SHORT).show()
            } else {
                showProgress(requireContext())
                presenter.reset_request(phone)
            }
        }
    }


    companion object {
        val TAG = "RestoreFragmentFirst"
        fun newInstance(): RestoreFragmentFirst {
            return RestoreFragmentFirst()
        }
    }

    override fun openSecondFragment(responsePhone: String) {
        removeProgress()
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.restorePageFirst,
            RestoreFragmentSecond.newInstance(responsePhone),
            RestoreFragmentSecond.TAG
        )
    }

    override fun showError() {
        removeProgress()
        Toast.makeText(context!!, "Неверный номер!", Toast.LENGTH_SHORT).show()
    }

}

package com.diploma.stats.views.auth.presenters.restore.third

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.diploma.stats.R
import com.diploma.stats.global.extension.replaceFragment
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.auth.presenters.login.LoginFragment
import com.diploma.stats.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_restore_third.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class RestoreFragmentThird() : BaseFragment(), RestoreThirdView {

    @InjectPresenter
    lateinit var presenter: RestoreThirdPresenter

    @ProvidePresenter
    fun providePresenter(): RestoreThirdPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.RESET_NEW_PASSWORD,
            named(AuthScope.RESET_NEW_PASSWORD)
        ).get()
    }

    override val layoutRes: Int = R.layout.fragment_restore_third

    override fun setUp(savedInstanceState: Bundle?) {
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }

        finishRestoreBtn.setOnClickListener {
            if (restorePassword.text.toString() == restorePasswordConfirm.text.toString()) {
                showProgress(requireContext())
                presenter.restore_new_password(
                    arguments?.getString(reset_phone)!!, arguments?.getString(
                        reset_token
                    )!!, restorePassword.text.toString()
                )
            } else {
                Toast.makeText(requireContext(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show()
            }
        }

        hideShow()
    }

    override fun onDestroy() {
        try {
            refreshData!!.invoke()
        }catch (e: Exception){
            e.printStackTrace()
        }
        getKoin().getScopeOrNull(AuthScope.RESET_NEW_PASSWORD)?.close()
        super.onDestroy()
    }

    override fun openLoginFrag() {
        removeProgress()
        activity?.supportFragmentManager?.replaceFragment(
            R.id.Container,
            LoginFragment.newInstance(),
            LoginFragment.TAG
        )
    }

    private fun hideShow() {

        imageView12.setOnClickListener {
            restorePassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView12.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            restorePassword.setSelection(restorePassword.text.length)
        }

        imageView.setOnClickListener {
            restorePassword.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView12.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            restorePassword.setSelection(restorePassword.text.length)
        }

        imageView13.setOnClickListener {
            restorePasswordConfirm.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            imageView13.visibility = View.GONE
            imageView14.visibility = View.VISIBLE
            restorePasswordConfirm.setSelection(restorePasswordConfirm.text.length)
        }

        imageView14.setOnClickListener {
            restorePasswordConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView13.visibility = View.VISIBLE
            imageView14.visibility = View.GONE
            restorePasswordConfirm.setSelection(restorePasswordConfirm.text.length)
        }
    }

    companion object {
        val TAG = "RestoreFragmentThird"
        const val reset_phone = "reset_phone"
        const val reset_token = "reset_token"
        var refreshData: (() -> Unit)? = null
        fun newInstance(loadData: () -> Unit, phone: String, resetToken: String): RestoreFragmentThird {
            val fragment = RestoreFragmentThird()
            refreshData = loadData
            val args = Bundle()
            args.putString(reset_phone, phone)
            args.putString(reset_token, resetToken)
            fragment.arguments = args
            return fragment
        }

    }

}
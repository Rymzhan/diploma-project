package com.thousand.bosch.views.auth.presenters.login

import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.auth.presenters.registration.first.RegistrationFragment
import com.thousand.bosch.views.auth.presenters.restore.first.RestoreFragmentFirst
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.thousand.bosch.global.base.BaseFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LoginFragment() : BaseFragment(), LoginView {
    private var handler: Handler? = null
    override val layoutRes: Int = R.layout.fragment_login
    var phone = ""

    private var firebaseToken: String? = ""

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return getKoin().getOrCreateScope(AuthScope.LOGIN_SCOPE, named(AuthScope.LOGIN_SCOPE)).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.LOGIN_SCOPE)?.close()
        super.onDestroy()
    }


    override fun setUp(savedInstanceState: Bundle?) {
        onClickListener()
    }


    companion object {
        val TAG = "LoginFragment"
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun openProfileFrag() {
        loginButton.isClickable = false
        createAccountBtn.isClickable = false
        forgetPasswordText.isClickable = false
        handler = Handler()
        handler!!.postDelayed({
            showProgressDialog(false)
        }, 300)
        showProgress(context!!)
        activity?.supportFragmentManager?.replaceFragment(
            R.id.Container,
            ProfileFragment.newInstance(),
            ProfileFragment.TAG
        )
    }

    override fun showError(message: String) {
        showProgressDialog(false)
        //Toast.makeText(context!!, message, Toast.LENGTH_SHORT).show()
        showMessage(message, requireView())
    }

    private fun onClickListener() {
        hideShow()

        MaskedTextChangedListener.installOn(
            loginNumber,
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

        loginButton.setSafeOnClickListener {
            Log.d("phoneCcheck", phone)
            if (phone.length < 10) {
                Toast.makeText(context!!, "Неверный формат номера!", Toast.LENGTH_SHORT).show()
            }
            if (loginPassword.text.toString().isEmpty()) {
                Toast.makeText(context!!, "Введённые данные не верны!", Toast.LENGTH_SHORT).show()
            }
            if (phone.length > 10 && !loginPassword.text.toString().isEmpty()) {
                showProgressDialog(true)
                closeKeyboard(activity)
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                        Log.d("Token Firebase", task.result!!)
                    presenter.login(
                        phone = phone,
                        password = loginPassword.text.toString(),
                        deviceToken = task.result!!
                    )
                })
            }
        }



        createAccountBtn.setSafeOnClickListener {
            closeKeyboard(requireActivity())
            showProgress(context!!)
            handler = Handler()
            handler!!.postDelayed({
                activity?.supportFragmentManager?.replaceFragment(
                    R.id.Container,
                    RegistrationFragment.newInstance(),
                    RegistrationFragment.TAG
                )
            }, 300)
        }

        forgetPasswordText.setSafeOnClickListener {
            closeKeyboard(requireActivity())
            showProgress(context!!)
            handler = Handler()
            handler!!.postDelayed({
                activity?.supportFragmentManager?.addFragmentWithBackStack(
                    R.id.login_page,
                    RestoreFragmentFirst.newInstance(),
                    RestoreFragmentFirst.TAG
                )
            }, 300)
        }

    }

    fun hideShow() {

        imageView12.setOnClickListener {
            loginPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView12.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            loginPassword.setSelection(loginPassword.text.length)
        }

        imageView.setOnClickListener {
            loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView12.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            loginPassword.setSelection(loginPassword.text.length)
        }
    }


}
package com.diploma.stats.views.auth.presenters.registration.second

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.diploma.stats.R
import com.diploma.stats.global.extension.addFragmentWithBackStack
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.auth.presenters.registration.third.RegistrationFragmentThird
import com.diploma.stats.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration_second.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class RegistrationFragmentSecond() : BaseFragment(), RegistrationFragmentSecondVIew {
    override val layoutRes: Int = R.layout.fragment_registration_second
    private var handler: Handler? = null
    private var timer: CountDownTimer? = null

    @InjectPresenter
    lateinit var presenter: RegistrationFragmentSecondPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationFragmentSecondPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.CONFIRM_REG_SCOPE,
            named(AuthScope.CONFIRM_REG_SCOPE)
        )
            .get()
    }

    override fun onDestroy() {
        try {
            timer!!.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getKoin().getScopeOrNull(AuthScope.CONFIRM_REG_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        closeKeyboard(activity!!)
        onClick()
    }

    @SuppressLint("SetTextI18n")
    private fun onClick() {
        textView8.text =
            resources.getString(R.string.sms_confirmation_code) + " +" + arguments?.getString(
                register_phone
            )
        handler = Handler()
        handler!!.postDelayed({
            removeProgress()
        }, 300)

        setTimer()

        sendAgain.setSafeOnClickListener {
            try {
                timer!!.cancel()
                presenter.register(arguments?.getString(register_phone)!!)
                setTimer()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        backToMain.setSafeOnClickListener {
            activity?.onBackPressed()
        }

        confirmSmsRegistrationBtn.setSafeOnClickListener {
            confirmCode = pinView.value.toString()
            if (confirmCode != null && confirmCode!!.length > 3) {
                showProgress(requireContext())
                timer!!.cancel()
                presenter.confirm_reg(arguments?.getString(register_phone)!!, confirmCode!!)
            } else {
                Toast.makeText(context!!, "Неверный формат ввода!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setTimer() {
        try {
            sendAgain.visibility = View.INVISIBLE
            timer = object : CountDownTimer(31000, 1000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    val tempInt: Int = (millisUntilFinished / 1000).toInt()
                    if (tempInt < 10) {
                        timerText.text = "0:0$tempInt"
                    } else {
                        timerText.text = "0:$tempInt"
                    }
                }

                override fun onFinish() {
                    sendAgain.visibility = View.VISIBLE
                }
            }
            timer!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    companion object {
        var confirmCode: String? = null
        val TAG = "RegistrationFragmentSecond"
        const val register_phone = "register_phone"
        fun newInstance(phone: String): RegistrationFragmentSecond {
            val fragment = RegistrationFragmentSecond()
            val args = Bundle()
            args.putString(register_phone, phone)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("LongLogTag")
    override fun openThird(regToken: String) {
        try {
            timer!!.cancel()
            removeProgress()
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.confirmRegistrationPage,
                RegistrationFragmentThird.newInstance(
                    { setTimer() },
                    arguments?.getString(register_phone)!!,
                    regToken
                ),
                RegistrationFragmentThird.TAG
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showError() {
        setTimer()
        removeProgress()
        Toast.makeText(context!!, "Пароль неверный или просрочен!", Toast.LENGTH_SHORT).show()
    }

    override fun showMessage() {
        Toast.makeText(context!!, "Пароль успешно отправлен", Toast.LENGTH_SHORT).show()
    }
}

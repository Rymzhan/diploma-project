package com.thousand.bosch.views.auth.presenters.restore.second

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.thousand.bosch.R
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.auth.presenters.restore.third.RestoreFragmentThird
import com.thousand.bosch.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_restore_second.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class RestoreFragmentSecond() : BaseFragment(), RestoreSecondView {

    override val layoutRes: Int = R.layout.fragment_restore_second
    private var handler: Handler? = null
    private var timer: CountDownTimer? = null

    @InjectPresenter
    lateinit var presenter: RestoreSecondPresenter

    @ProvidePresenter
    fun providePresenter(): RestoreSecondPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.RESET_CONFIRM_SCOPE,
            named(AuthScope.RESET_CONFIRM_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.RESET_CONFIRM_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        onClick()
    }

    @SuppressLint("SetTextI18n")
    private fun onClick() {
        textView888.text =
            resources.getString(R.string.sms_confirmation_code) + " +" + arguments?.getString(
                reset_phone
            )

        setTimer()

        sendAgain.setSafeOnClickListener {
            try {
                timer!!.cancel()
                presenter.reset_request(arguments?.getString(reset_phone)!!)
                setTimer()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        backToMain.setOnClickListener {
            requireActivity().onBackPressed()
        }

        confirmSmsRestoreBtn.setSafeOnClickListener {
            confirmCode = pinView2.value.toString()
            if (confirmCode != null && confirmCode!!.length > 3) {
                showProgress(requireContext())
                timer!!.cancel()
                presenter.reset_confirm(arguments?.getString(reset_phone)!!, confirmCode!!)
            } else {
                Toast.makeText(context!!, "Неверный формат ввода!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun goToThird(resetToken: String) {
        removeProgress()
        activity?.supportFragmentManager?.addFragmentWithBackStack(
            R.id.restorePageSecond,
            RestoreFragmentThird.newInstance(
                { setTimer() },
                arguments?.getString(reset_phone)!!,
                resetToken
            ),
            RestoreFragmentThird.TAG
        )
    }

    companion object {
        var confirmCode: String? = null
        val TAG = "RestoreFragmentSecond"
        const val reset_phone = "reset_phone"
        fun newInstance(phone: String): RestoreFragmentSecond {
            val fragment = RestoreFragmentSecond()
            val args = Bundle()
            args.putString(reset_phone, phone)
            fragment.arguments = args
            return fragment
        }

    }

    private fun setTimer() {
        try {
            sendAgain.visibility = View.INVISIBLE
            timer = object : CountDownTimer(31000, 1000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    try {

                        val tempInt: Int = (millisUntilFinished / 1000).toInt()
                        if (tempInt < 10) {
                            timerText23.text = "0:0$tempInt"
                        } else {
                            timerText23.text = "0:$tempInt"
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFinish() {
                    try {
                        sendAgain.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            timer!!.start()
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
        removeProgress()
        Toast.makeText(context!!, "Пароль успешно отправлен", Toast.LENGTH_SHORT).show()
    }
}


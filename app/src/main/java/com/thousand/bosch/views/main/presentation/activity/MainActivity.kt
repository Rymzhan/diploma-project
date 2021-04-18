package com.thousand.bosch.views.main.presentation.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.messaging.FirebaseMessaging
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.*
import com.thousand.bosch.global.extension.clearAndReplaceFragment
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.auth.presenters.login.LoginFragment
import com.thousand.bosch.views.main.presentation.first.BlankFragment
import com.thousand.bosch.views.main.presentation.game.round.friend.ResultsFriendFragment
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import tech.gusavila92.websocketclient.WebSocketClient
import timber.log.Timber


class MainActivity : MvpAppCompatActivity(), MainActivityView {

    private val TAG = "MainActivity"

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter(): MainActivityPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.MAIN_ACTIVITY_SCOPE,
            named(AuthScope.MAIN_ACTIVITY_SCOPE)
        ).get()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.clearAndReplaceFragment(
            R.id.Container,
            BlankFragment.newInstance(),
            BlankFragment.TAG)

    }

    override fun showProgressBar(show: Boolean) = progressBar.visible(show)

}

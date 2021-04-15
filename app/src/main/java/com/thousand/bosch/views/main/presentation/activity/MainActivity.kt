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

    private var webSocketClient: WebSocketClient? = null

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


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra("game_id")?.let { it1 ->
            val tempId = it1.toInt()
            Log.d("Game Id Notification 2", it1)
            if (tempId > 0) {
                if (LocalStorage.getAccessToken() != "tokasen") {
                    supportFragmentManager.clearAndReplaceFragment(
                        R.id.Container,
                        ResultsFriendFragment.newInstance(0, tempId),
                        ResultsFriendFragment.TAG
                    )
                } else {
                    supportFragmentManager.replaceFragment(
                        R.id.Container,
                        LoginFragment.newInstance(),
                        LoginFragment.TAG
                    )
                }
            } else {
                setUp()
            }
        }
        return
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "check")
    }

    private fun setUp() {
        if (LocalStorage.getAccessToken() != "tokasen") {
            supportFragmentManager.clearAndReplaceFragment(
                R.id.Container,
                ProfileFragment.newInstance(),
                ProfileFragment.TAG
            )
        } else {
            supportFragmentManager.replaceFragment(
                R.id.Container,
                LoginFragment.newInstance(),
                LoginFragment.TAG
            )
        }
    }

    override fun showProgressBar(show: Boolean) = progressBar.visible(show)

}

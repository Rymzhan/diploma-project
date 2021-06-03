package com.diploma.stats.views.main.presentation.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.extension.*
import com.diploma.stats.global.extension.clearAndReplaceFragment
import com.diploma.stats.views.scope.di.AuthScope
import com.diploma.stats.views.main.presentation.first.BlankFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


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

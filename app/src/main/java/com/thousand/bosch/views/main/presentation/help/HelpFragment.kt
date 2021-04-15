package com.thousand.bosch.views.main.presentation.help

import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.model.web_view.Data
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.web_view.WebViewFragment
import kotlinx.android.synthetic.main.fragment_help.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class HelpFragment : BaseFragment(), HelpView {
    private val listOfTextView: List<TextView> = listOf(
        helpAccountText,
        helpGameText,
        helpStatisticsText,
        helpNotificationsText,
        helpAnotherText
    )

    companion object {
        val TAG = "HelpFragment"
        fun newInstance(): HelpFragment {
            return HelpFragment()
        }
    }

    @InjectPresenter
    lateinit var presenter: HelpPresenter

    @ProvidePresenter
    fun providePresenter(): HelpPresenter {
        getKoin().getScopeOrNull(AuthScope.HELP_SCOPE)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.HELP_SCOPE,
            named(AuthScope.HELP_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.HELP_SCOPE)?.close()
        super.onDestroy()
    }

    override val layoutRes: Int = R.layout.fragment_help

    override fun setUp(savedInstanceState: Bundle?) {
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }
        presenter.getWebView()
    }

    override fun bindWebView(data: List<Data>) {
        val listOfConstraints: List<ConstraintLayout> = listOf(
            helpAccountButton,
            helpGameButton,
            helpStatisticsButton,
            helpNotificationsButton,
            helpAnotherButton
        )
        for (i in data.indices) {
            listOfConstraints[i].setSafeOnClickListener {
                showProgressBar(true)
                requireActivity().supportFragmentManager.addFragmentWithBackStack(
                    R.id.Container,
                    WebViewFragment.newInstance(data[i]),
                    WebViewFragment.TAG
                )
            }
        }
    }


}

package com.thousand.bosch.views.main.presentation.game.results.main.top

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.model.list.top.Data
import com.thousand.bosch.views.adapters.TopTwentyAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_top_list.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class TopListFragment() : BaseFragment(), TopListView {

    override val layoutRes: Int = R.layout.fragment_top_list

    @InjectPresenter
    lateinit var presenter: TopListPresenter

    @ProvidePresenter
    fun providePresenter(): TopListPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.TOP_LIST_SCOPE,
            named(AuthScope.TOP_LIST_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.TOP_LIST_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgressDialog(true)
        presenter.getTopList()
    }

    override fun setView(list: List<Data>) {
        val adapter = TopTwentyAdapter(requireContext(),requireActivity())
        adapter.pushAdapter(list)
        adapter.notifyDataSetChanged()
        val recyclerView = topListRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        showProgressDialog(false)
    }

}
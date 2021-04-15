package com.thousand.bosch.views.main.presentation.game.results.main.friends

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.model.list.top.Data
import com.thousand.bosch.views.adapters.TopTwentyAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_friends_top_list.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class FriendsTopListFragment : BaseFragment(), FriendsTopListView {
    override val layoutRes: Int = R.layout.fragment_friends_top_list

    @InjectPresenter
    lateinit var presenter: FriendsTopListPresenter

    @ProvidePresenter
    fun providePresenter(): FriendsTopListPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.FRIENDS_TOP_LIST_SCOPE,
            named(AuthScope.FRIENDS_TOP_LIST_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.FRIENDS_TOP_LIST_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgressDialog(true)
        presenter.getTopFriends()
    }

    override fun setView(list: List<Data>) {
        val adapter = TopTwentyAdapter(requireContext(),requireActivity())
        adapter.pushAdapter(list)
        adapter.notifyDataSetChanged()
        val recyclerView = topListFriendsRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        showProgressDialog(false)
    }

}
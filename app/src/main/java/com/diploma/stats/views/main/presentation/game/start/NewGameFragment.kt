package com.diploma.stats.views.main.presentation.game.start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.global.extension.replaceFragmentWithBackStack
import com.diploma.stats.global.utils.LocalStorage
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.search.SearchUser
import com.diploma.stats.views.adapters.FriendsListAdapter
import com.diploma.stats.views.adapters.SearchUsersAdapter
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.main.presentation.game.round.friend.ResultsFriendFragment
import kotlinx.android.synthetic.main.fragment_new_game.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class NewGameFragment() : BaseFragment(), NewGameView {

    override val layoutRes: Int = R.layout.fragment_new_game

    companion object {
        val TAG = "NewGameFragment"
        fun newInstance(): NewGameFragment {
            return NewGameFragment()
        }
    }

    @InjectPresenter
    lateinit var presenter: NewGamePresenter

    var tempInstance: Bundle? = null

    @ProvidePresenter
    fun providePresenter(): NewGamePresenter {
        return getKoin().getOrCreateScope(
            AuthScope.NEW_GAME_SCOPE,
            named(AuthScope.NEW_GAME_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.NEW_GAME_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }
        presenter.loadData()

        val search: SearchView = searchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    presenter.loadData()
                    friendsScrollSearch.visibility = View.GONE
                    friendsScroll.visibility = View.VISIBLE
                } else {
                    presenter.setText(newText)
                    presenter.loadDataSearch()
                    friendsScrollSearch.visibility = View.VISIBLE
                    friendsScroll.visibility = View.GONE
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        randomButton.setSafeOnClickListener {
            showProgress(context!!)
            presenter.randomGame()
        }

        whatsAppLayout.setSafeOnClickListener {
            try {
                showProgress(requireContext())
                val shareBody = LocalStorage.getInvite()
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                sendIntent.type = "text/plain"
                sendIntent.setPackage("com.whatsapp")
                startActivity(sendIntent)
                removeProgress()
            } catch (e: Exception) {
                showMessage("Установите Whatsapp!", requireView())
                removeProgress()
                e.printStackTrace()
            }
        }

        telegramLayout.setSafeOnClickListener {
            try {
                showProgress(requireContext())
                val shareBody = LocalStorage.getInvite()
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                sendIntent.type = "text/plain"
                sendIntent.setPackage("org.telegram.messenger")
                startActivity(sendIntent)
                removeProgress()
            } catch (e: Exception) {
                showMessage("Установите Telegram!", requireView())
                removeProgress()
                e.printStackTrace()
            }
        }

        smsLayout.setSafeOnClickListener {
            try {
                showProgress(requireContext())
                val shareBody = LocalStorage.getInvite()
                val sendIntent = Intent(Intent.ACTION_VIEW)
                sendIntent.putExtra("sms_body", shareBody)
                sendIntent.type = "vnd.android-dir/mms-sms"
                requireActivity().startActivity(sendIntent)
                removeProgress()
            } catch (e: Exception) {
                e.printStackTrace()
                removeProgress()
                showMessage("Установите данное приложение!", requireView())
            }
        }
    }

    override fun bindFriendsRecycler(friendsList: List<DataX>) {
        val recyclerView = friendsRecyclerView as RecyclerView
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter =
            FriendsListAdapter(
                {},
                {closeKeyboard(requireActivity())},
                friendsList,
                context!!,
                activity!!.supportFragmentManager,
                R.id.new_game_page
            )
        removeProgress()

        newGameScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            val scrollViewLi = checkNotNull(v) {
                return@setOnScrollChangeListener
            }

            val lastChild = scrollViewLi.getChildAt(scrollViewLi.childCount - 1)

            if (lastChild != null) {
                if ((scrollY >= (lastChild.measuredHeight - scrollViewLi.measuredHeight)) && scrollY > oldScrollY) {
                    presenter.loadDataNextPage()
                }
            }
        }


    }

    override fun goToResultsPage(id: Int) {
        removeProgress()
        activity?.supportFragmentManager?.replaceFragmentWithBackStack(
            R.id.new_game_page,
            ResultsFriendFragment.newInstance(0, id),
            ResultsFriendFragment.TAG
        )
    }

    override fun showPD(show: Boolean) {
        showProgressBar(show)
    }

    override fun bindRecycler(userList: List<SearchUser>) {
        val recyclerView: RecyclerView? = friendsRecyclerViewSearch
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = SearchUsersAdapter(
            {closeKeyboard(requireActivity())},
            userList,
            context!!,
            activity?.supportFragmentManager!!,
            R.id.new_game_page
        )

        newGameScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            val scrollViewLi = checkNotNull(v) {
                return@setOnScrollChangeListener
            }

            val lastChild = scrollViewLi.getChildAt(scrollViewLi.childCount - 1)

            if (lastChild != null) {
                if ((scrollY >= (lastChild.measuredHeight - scrollViewLi.measuredHeight)) && scrollY > oldScrollY) {
                    presenter.loadDataNextPageSearch()
                }
            }
        }
    }
}

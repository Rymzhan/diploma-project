package com.thousand.bosch.views.main.presentation.profile.friends.list

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.search.SearchUser
import com.thousand.bosch.views.adapters.FriendsListAdapter
import com.thousand.bosch.views.adapters.SearchUsersAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_friends_list.*
import kotlinx.android.synthetic.main.fragment_friends_list.backToMain
import kotlinx.android.synthetic.main.fragment_friends_list.friendsRecyclerView
import kotlinx.android.synthetic.main.fragment_friends_list.searchView
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.util.*


class FriendsListFragment() : BaseFragment(), FriendsListView {

    override val layoutRes: Int = R.layout.fragment_friends_list
    private lateinit var random: Random
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    @InjectPresenter
    lateinit var presenter: FriendsListPresenter

    @ProvidePresenter
    fun providePresenter(): FriendsListPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.SEARCH_USERS,
            named(AuthScope.SEARCH_USERS)
        ).get()
    }

    override fun onDestroy() {
        try {
            refreshData!!.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getKoin().getScopeOrNull(AuthScope.SEARCH_USERS)?.close()
        super.onDestroy()
    }

    companion object {
        val TAG = "FriendsListFragment"
        private var bol: Boolean? = null
        var refreshData: (() -> Unit)? = null
        fun newInstance(loadData: () -> Unit): FriendsListFragment {
            refreshData = loadData
            return FriendsListFragment()
        }
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgressBar(true)
        //presenter.list_friends()
        presenter.loadData()
        onClick()
        showProgressBar(false)
        random = Random()
        handler = Handler()
        friendsListRefresh.setOnRefreshListener {
            runnable = Runnable {
                presenter.loadData()
                onClick()
                friendsListRefresh.isRefreshing = false
            }
            handler.postDelayed(
                runnable, 1000.toLong()
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        //presenter.list_friends()
        onClick()
    }

    private fun onClick() {
        backToMain.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        val search: SearchView = searchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    presenter.loadData()
                    friendsRecyclerView.visibility = View.GONE
                    myFriendsRecycler.visibility = View.VISIBLE
                } else {
                    presenter.setText(newText)
                    myFriendsRecycler.visibility = View.GONE
                    friendsRecyclerView.visibility = View.VISIBLE
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })

        whatsAppLayout1.setSafeOnClickListener {
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

        telegramLayout1.setSafeOnClickListener {
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

        smsLayout1.setSafeOnClickListener {
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
                showMessage("Установите приложение!", requireView())
            }
        }
    }


    override fun bindRecycler(userList: List<SearchUser>) {
        val recyclerView: RecyclerView? = friendsRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = SearchUsersAdapter(
            {closeKeyboard(requireActivity())},
            userList,
            context!!,
            activity?.supportFragmentManager!!,
            R.id.friends_list_page
        )

        if (friendsListScroll != null) {
            friendsListScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

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

    override fun showPD(show: Boolean) {
        showProgressBar(show)
    }

    override fun bindFriendsRecycler(friendsList: List<DataX>) {
        val recyclerView: RecyclerView? = myFriendsRecycler
        if (friendsList.isEmpty()) {
            recyclerView?.visibility = View.GONE
            ifEmptyText.visibility = View.VISIBLE
        } else {
            ifEmptyText.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(context)
            recyclerView?.adapter = FriendsListAdapter(
                { presenter.loadData() },
                { closeKeyboard(requireActivity()) },
                friendsList,
                context!!,
                activity!!.supportFragmentManager,
                R.id.friends_list_page
            )

            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels

            if (friendsListScroll != null) {
                friendsListScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

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
        }
    }


}
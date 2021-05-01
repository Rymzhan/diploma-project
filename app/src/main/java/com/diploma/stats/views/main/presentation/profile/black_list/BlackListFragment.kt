package com.diploma.stats.views.main.presentation.profile.black_list

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.diploma.stats.R
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.views.adapters.BlackListAdapter
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_black_list.*
import kotlinx.android.synthetic.main.fragment_black_list.backToMain
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class BlackListFragment() : BaseFragment(), BlackListView {

    override val layoutRes: Int = R.layout.fragment_black_list
    var unblock: MutableList<Int>? = mutableListOf()

    @InjectPresenter
    lateinit var presenter: BlackListPresenter

    @ProvidePresenter
    fun providePresenter(): BlackListPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.BLACKLIST_SCOPE,
            named(AuthScope.BLACKLIST_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.BLACKLIST_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgress(context!!)
        val nestedScroll1: NestedScrollView? = nestedBlackList
        presenter.loadData()
        val nestedScroll2: NestedScrollView? = nestedEditBlack
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }

        editBlackListButton.setSafeOnClickListener {
            showProgress(context!!)
            editBlackListButton.visibility = View.GONE
            cancelButton.visibility = View.VISIBLE
            nestedScroll1?.visibility = View.GONE
            nestedScroll2?.visibility = View.VISIBLE
            delFromBlackList.visibility = View.VISIBLE
            removeProgress()
            delFromBlackList.setOnClickListener {
                showProgress(context!!)
                try {
                    presenter.delete(unblock!!)
                } catch (e: Exception) {
                    removeProgress()
                    e.printStackTrace()
                }
            }
        }

        cancelButton.setSafeOnClickListener {
            showProgress(context!!)
            editBlackListButton.visibility = View.VISIBLE
            cancelButton.visibility = View.GONE
            nestedScroll1?.visibility = View.VISIBLE
            nestedScroll2?.visibility = View.GONE
            delFromBlackList.visibility = View.GONE
            removeProgress()
        }

    }

    override fun bindList(blackList: List<DataX>) {
        val recycler1: RecyclerView? = blackListRecycler
        val recycler2: RecyclerView? = editBlackListRecycler
        val nestedScroll1: NestedScrollView? = nestedBlackList
        val nestedScroll2: NestedScrollView? = nestedEditBlack
        nestedScroll1?.visibility = View.VISIBLE
        editBlackListButton.visibility = View.VISIBLE
        cancelButton.visibility = View.GONE
        nestedScroll2?.visibility = View.GONE
        delFromBlackList.visibility = View.GONE
        val adapter1 =
            BlackListAdapter(
                { presenter.loadDataNextPage() },
                blackList,
                context!!,
                object : BlackListAdapter.OnItemCheckListener {
                    override fun onItemCheck(toUnblock: List<Int>) {
                        unblock = toUnblock as MutableList<Int>
                    }

                    override fun onItemUncheck(toUnblock: List<Int>) {
                        unblock = toUnblock as MutableList<Int>
                    }

                },
                1
            )
        recycler1?.layoutManager = LinearLayoutManager(context)
        recycler1?.adapter = adapter1
        recycler1!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    //  presenter.loadDataNextPage()
                }
            }
        })


        nestedScroll2?.visibility = View.GONE
        val adapter2 =
            BlackListAdapter(
                { presenter.loadDataNextPage() },
                blackList,
                context!!,
                object : BlackListAdapter.OnItemCheckListener {
                    override fun onItemCheck(toUnblock: List<Int>) {
                        unblock = toUnblock as MutableList<Int>
                    }

                    override fun onItemUncheck(toUnblock: List<Int>) {
                        unblock = toUnblock as MutableList<Int>
                    }

                },
                2
            )
        recycler2?.layoutManager = LinearLayoutManager(context)
        recycler2?.adapter = adapter2
        recycler2!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    //presenter.loadDataNextPage()
                }
            }
        })


        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels

        if (blackListScroll != null) {
            blackListScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

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

        removeProgress()
    }

    override fun showPD(show: Boolean) {
        showProgressBar(show)
    }

    companion object {
        val TAG = "BlackListFragment"
        fun newInstance(): BlackListFragment {
            return BlackListFragment()
        }
    }

}

package com.thousand.bosch.views.main.presentation.profile.block_user

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.thousand.bosch.R
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.views.adapters.BlockUserAdapter
import com.thousand.bosch.views.adapters.BlockUserAdapter.OnItemCheckListener
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_block_user.*
import kotlinx.android.synthetic.main.fragment_block_user.backToMain
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class BlockUserFragment() : BaseFragment(), BlockUserView {

    override val layoutRes: Int = R.layout.fragment_block_user
    var block: MutableList<Int>? = mutableListOf()
    var unblock: MutableList<Int>? = mutableListOf()
    var tempList: List<DataX>? = null
    //lateinit var blockedList: List<Int>

    @InjectPresenter
    lateinit var presenter: BlockUserPresenter

    @ProvidePresenter
    fun providePresenter(): BlockUserPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.BLOCK_USER_SCOPE,
            named(AuthScope.BLOCK_USER_SCOPE)
        ).get()
    }

    companion object {
        val TAG = "BlockUserFragment"
        fun newInstance(): BlockUserFragment {
            return BlockUserFragment()
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.BLOCK_USER_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgress(context!!)
        backToMain.setOnClickListener {
            activity?.onBackPressed()
        }

        presenter.loadData()


        blockButton.setSafeOnClickListener {
            try {
                showProgress(context!!)
                presenter.blockList(block!!, unblock!!)
            } catch (e: Exception) {
                removeProgress()
                e.printStackTrace()
            }
        }
    }

    override fun bindList(friendsList: List<DataX>) {
        tempList = friendsList
        block = null
        unblock = null
        val recyclerView: RecyclerView? = usersToBlockRecycler
        if (friendsList.isEmpty()) {
            recyclerView?.visibility = View.GONE
        } else {
            recyclerView?.visibility = View.VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(context)
            val adapter = BlockUserAdapter(
                { presenter.loadDataNextPage() },
                friendsList,
                context!!,
                object : OnItemCheckListener {
                    override fun onItemCheck(toBlock: List<Int>, toUnblock: List<Int>) {
                        block = toBlock as MutableList<Int>
                        unblock = toUnblock as MutableList<Int>
                    }

                    override fun onItemUncheck(toBlock: List<Int>, toUnblock: List<Int>) {
                        block = toBlock as MutableList<Int>
                        unblock = toUnblock as MutableList<Int>
                    }
                })
            recyclerView?.adapter = adapter

            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels

            if (blockUserScroll != null) {
                blockUserScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

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
        removeProgress()
    }

    override fun showPD(show: Boolean) {
        showProgressBar(show)
    }

    override fun goToSettings() {
        removeProgress()
    }


}

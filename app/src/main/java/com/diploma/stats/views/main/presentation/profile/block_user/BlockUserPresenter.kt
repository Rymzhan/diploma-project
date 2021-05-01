package com.diploma.stats.views.main.presentation.profile.block_user

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.presentation.Paginator
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.views.auth.interactors.UserInteractor

@Suppress("UNCHECKED_CAST")
@InjectViewState
class BlockUserPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<BlockUserView>() {

    val TAG = "BlockUserPresenter"

    private val paginator = Paginator(
        {
            userInteractor.list_friends2(it)
        },
        object : Paginator.ViewController<DataX> {
            override fun showEmptyProgress(show: Boolean) {}

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.bindList(emptyList())
            }

            override fun showEmptyView(show: Boolean) {
                viewState.bindList(emptyList())}

            override fun showErrorMessage(error: Throwable) {viewState.bindList(emptyList())
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showPD(show)
            }

            override fun showPageProgress(show: Boolean) {
                viewState.showPD(show)
            }

            override fun showData(show: Boolean, data: List<DataX>) {
                if (show)
                    viewState.bindList(data)
            }
        }
    )

    fun loadData() = paginator.refresh()

    fun loadDataNextPage() {
        paginator.loadNewPage().toString()
    }

    fun blockList(list1: MutableList<Int>, list2: MutableList<Int>) {

        userInteractor.blockList(list1).subscribe({ it1 ->
            if (it1.isSuccessful) {
                loadData()
            }
        }, {
            it.printStackTrace()
        }).connect()

        userInteractor.removeList(list2).subscribe({
            if (it.isSuccessful) {
                loadData()
            }
        }, {
            it.printStackTrace()
        }).connect()



    }


}
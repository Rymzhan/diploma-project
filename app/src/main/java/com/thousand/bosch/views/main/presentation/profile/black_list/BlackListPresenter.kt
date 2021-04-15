package com.thousand.bosch.views.main.presentation.profile.black_list

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.global.presentation.Paginator
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class BlackListPresenter(private val userInteractor: UserInteractor): BasePresenter<BlackListView>(){
    val TAG = "BlackListPresenter"

    private val paginator = Paginator(
        {
            userInteractor.blacklist2(it)
        },
        object : Paginator.ViewController<DataX> {
            override fun showEmptyProgress(show: Boolean) {}

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.bindList(emptyList())
            }

            override fun showEmptyView(show: Boolean) {
                viewState.bindList(emptyList())
            }

            override fun showErrorMessage(error: Throwable) {
                viewState.bindList(emptyList())
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

    fun delete(list2: MutableList<Int>){
        userInteractor.removeList(list2).subscribe({
            if (it.isSuccessful) {
                loadData()
            }
        }, {
            it.printStackTrace()
        }).connect()
    }
}
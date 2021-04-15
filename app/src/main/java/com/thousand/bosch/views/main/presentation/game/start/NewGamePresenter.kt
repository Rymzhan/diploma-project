package com.thousand.bosch.views.main.presentation.game.start

import com.arellomobile.mvp.InjectViewState
import com.thousand.bosch.global.presentation.BasePresenter
import com.thousand.bosch.global.presentation.Paginator
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.search.SearchUser
import com.thousand.bosch.views.auth.interactors.UserInteractor

@InjectViewState
class NewGamePresenter(private val userInteractor: UserInteractor) : BasePresenter<NewGameView>() {

    val TAG = "NewGamePresenter"

    private var queryText = ""

    fun randomGame() {
        userInteractor.gameWithRandom().subscribe({
            if (it.isSuccessful) {
                val tempResponse = it.body()!!
                viewState.goToResultsPage(tempResponse.data.id)
            }
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun setText(text: String) {
        queryText = text
    }

    private val paginator = Paginator(
        {
            userInteractor.list_friends2(it)
        },
        object : Paginator.ViewController<DataX> {
            override fun showEmptyProgress(show: Boolean) {}

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.bindFriendsRecycler(emptyList())
            }

            override fun showEmptyView(show: Boolean) {
                viewState.bindFriendsRecycler(emptyList())
            }

            override fun showErrorMessage(error: Throwable) {
                viewState.bindFriendsRecycler(emptyList())
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showPD(show)
            }

            override fun showPageProgress(show: Boolean) {
                viewState.showPD(show)
            }

            override fun showData(show: Boolean, data: List<DataX>) {
                if (show)
                    viewState.bindFriendsRecycler(data)
            }
        }
    )

    fun loadData() = paginator.refresh()

    fun loadDataNextPage() {
        paginator.loadNewPage().toString()
    }

    private val paginatorSearch = Paginator(
        {
            userInteractor.search_user("smth", queryText, it)
        },
        object : Paginator.ViewController<SearchUser> {
            override fun showEmptyProgress(show: Boolean) {}

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState.bindRecycler(emptyList())
            }

            override fun showEmptyView(show: Boolean) {
                viewState.bindRecycler(emptyList())
            }

            override fun showErrorMessage(error: Throwable) {
                viewState.bindRecycler(emptyList())
            }

            override fun showRefreshProgress(show: Boolean) {
                viewState.showPD(show)
            }

            override fun showPageProgress(show: Boolean) {
                viewState.showPD(show)
            }

            override fun showData(show: Boolean, data: List<SearchUser>) {
                if (show) {
                    viewState.bindRecycler(data)
                }
            }
        }
    )

    fun loadDataSearch() = paginatorSearch.refresh()

    fun loadDataNextPageSearch() {
        paginatorSearch.loadNewPage().toString()
    }
}
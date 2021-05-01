package com.diploma.stats.views.main.presentation.profile.friends.list

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.presentation.Paginator
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.search.SearchUser
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class FriendsListPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<FriendsListView>() {

    val TAG = "FriendsListPresenter"

    private var queryText = ""

    fun setText(text: String) {
        queryText = text
        if (queryText.isNotEmpty())
            loadDataSearch()
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
                if (show)
                    viewState.bindRecycler(data)
            }
        }
    )

    fun loadDataSearch() = paginatorSearch.refresh()

    fun loadDataNextPageSearch() {
        paginatorSearch.loadNewPage().toString()
    }


}
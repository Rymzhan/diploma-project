package com.diploma.stats.views.main.presentation.stats.department.course

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.presentation.Paginator
import com.diploma.stats.model.department.dep_list.CourseResult
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class StatsByCoursePresenter(private val userInteractor: UserInteractor) : BasePresenter<StatsByCourseView>() {

    private var sortKey = "decrease"

    fun setSortKey(key: String){
        sortKey = key
        loadData()
    }

    private val paginator = Paginator(
        {
            userInteractor.statsByCourses(sortKey,it)
        },
        object : Paginator.ViewController<CourseResult> {
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

            override fun showData(show: Boolean, data: List<CourseResult>) {
                if (show)
                    viewState.bindRecycler(data)
            }
        }
    )

    fun loadData() = paginator.refresh()

    fun loadDataNextPage() {
        paginator.loadNewPage().toString()
    }

}
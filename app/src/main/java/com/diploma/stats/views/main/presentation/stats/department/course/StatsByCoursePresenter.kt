package com.diploma.stats.views.main.presentation.stats.department.course

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.presentation.Paginator
import com.diploma.stats.model.department.dep_list.CourseResult
import com.diploma.stats.views.scope.interactors.UserInteractor

@InjectViewState
class StatsByCoursePresenter(private val userInteractor: UserInteractor) : BasePresenter<StatsByCourseView>() {

    private var sortKey = "decrease"
    var departmentId: Int? = null
    var courseId: Int? = null

    fun setSortKey(key: String){
        sortKey = key
        loadData()
    }

    private val paginator = Paginator(
        {
            userInteractor.statsByCourses(sortKey,it,departmentId,courseId)
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

    fun getDepartmentList(){
        userInteractor.departmentList().subscribe({response->
            viewState.bindDepartment(response)
        },{t->
            t.printStackTrace()
        }).connect()
    }

    fun getCoursesList(department_id: Int) {
        userInteractor.getCourses2(department_id).subscribe({response->
            viewState.bindCourses(response)
        },{t->
            t.printStackTrace()
        }).connect()
    }

    fun setDepartment(departmentId: Int?) {
        this.departmentId = departmentId
        courseId = null
        loadData()
        if(departmentId!=null){
            getCoursesList(departmentId)
        }
    }

    fun setCurrentCourse(courseId: Int?) {
        this.courseId = courseId
        loadData()
    }

}
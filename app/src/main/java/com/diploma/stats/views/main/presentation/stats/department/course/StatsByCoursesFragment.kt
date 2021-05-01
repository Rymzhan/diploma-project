package com.diploma.stats.views.main.presentation.stats.department.course

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.model.department.dep_list.CourseResult
import com.diploma.stats.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_stats_by_courses.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class StatsByCoursesFragment : BaseFragment(), StatsByCourseView {

    override val layoutRes: Int = R.layout.fragment_stats_by_courses

    @InjectPresenter
    lateinit var presenter: StatsByCoursePresenter

    @ProvidePresenter
    fun providePresenter(): StatsByCoursePresenter {
        return getKoin().getOrCreateScope(AuthScope.COURSE_STATS_SCOPE, named(AuthScope.COURSE_STATS_SCOPE))
            .get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.COURSE_STATS_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        showProgressBar(true)
        initSpinner()
        presenter.loadData()
        backToMainCourse.setSafeOnClickListener { requireActivity().onBackPressed() }
    }

    private fun initSpinner() {
        val recyclerView: RecyclerView? = courseRecyclerView

        val sortList: List<String> = listOf("Сначала легкие", "Сначала трудные")

        val adapter = ArrayAdapter(context!!, R.layout.spinner_item, sortList)

        sortCourseSpinner.adapter = adapter

        sortCourseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                recyclerView?.adapter = null
                when(position){
                    0->{
                        presenter.setSortKey("decrease")
                    }
                    1->{
                        presenter.setSortKey("increase")
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "StatsByCoursesFragment"

        @JvmStatic
        fun newInstance() = StatsByCoursesFragment()
    }

    override fun showPD(show: Boolean) {
        showProgressBar(show)
    }

    override fun bindRecycler(userList: List<CourseResult>) {
        val recyclerView: RecyclerView? = courseRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = StatsByCourseAdapter(userList)
        showProgressBar(false)

        if (courseNestedScroll != null) {
            courseNestedScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

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
package com.diploma.stats.views.main.presentation.stats.student.city

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
import com.diploma.stats.model.department.dep_list.StudentCityResult
import com.diploma.stats.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_stats_by_city.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class StatsByCityFragment : BaseFragment(), StatsByCityView {

    override val layoutRes: Int = R.layout.fragment_stats_by_city

    @InjectPresenter
    lateinit var presenter: StatsByCityPresenter

    @ProvidePresenter
    fun providePresenter(): StatsByCityPresenter {
        return getKoin().getOrCreateScope(AuthScope.CITY_STATS_SCOPE, named(AuthScope.CITY_STATS_SCOPE))
            .get()
    }

    override fun onDestroy() {
        showPD(false)
        getKoin().getScopeOrNull(AuthScope.CITY_STATS_SCOPE)?.close()
        super.onDestroy()
    }


    override fun setUp(savedInstanceState: Bundle?) {
        showProgressBar(true)
        initSpinner()
        presenter.loadData()
        backToMainCity.setSafeOnClickListener {
            showPD(false)
            requireActivity().onBackPressed()
        }
    }

    private fun initSpinner() {
        val recyclerView: RecyclerView? = cityRecyclerView

        val sortList: List<String> = listOf("По убыванию количества абитуриентов", "По возрастанию количества абитуриентов")

        val adapter = ArrayAdapter(context!!, R.layout.spinner_item, sortList)

        sortStudentSpinner.adapter = adapter

        sortStudentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    override fun showPD(show: Boolean) {
        showProgressBar(show)
    }

    override fun bindRecycler(userList: List<StudentCityResult>) {
        val recyclerView: RecyclerView? = cityRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = StatsByCityAdapter(userList)
        showProgressBar(false)

        if (cityNestedScroll != null) {
            cityNestedScroll.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

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

    companion object {

        const val TAG = "StatsByCityFragment"

        @JvmStatic
        fun newInstance() = StatsByCityFragment()
    }
}
package com.thousand.bosch.views.main.presentation.stats.department.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.model.department.response.DepartmentResponse
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.stats.department.pre.PreDepartmentPresenter
import kotlinx.android.synthetic.main.fragment_post_department.*
import kotlinx.android.synthetic.main.fragment_post_department.backToMain
import kotlinx.android.synthetic.main.fragment_pre_department.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PostDepartmentFragment : BaseFragment(), PostDepartmentView {
    private var group_id: Int? = null
    private var course_id: Int? = null

    @InjectPresenter
    lateinit var presenter: PostDepartmentPresenter

    @ProvidePresenter
    fun providePresenter(): PostDepartmentPresenter {
        return getKoin().getOrCreateScope(AuthScope.POST_DEP_SCOPE, named(AuthScope.POST_DEP_SCOPE))
            .get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.POST_DEP_SCOPE)?.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            group_id = it.getInt(ARG_PARAM1)
            course_id = it.getInt(ARG_PARAM2)
        }
    }

    override val layoutRes: Int = R.layout.fragment_post_department

    override fun setUp(savedInstanceState: Bundle?) {
        group_id?.let { groupId ->
            course_id?.let { courseId ->
                presenter.getCalcByGroup(groupId, courseId)
            }
        }

        backToMain.setSafeOnClickListener { requireActivity().onBackPressed() }
    }

    override fun bindResult(departmentResponse: DepartmentResponse) {
//        Toast.makeText(requireContext(), departmentResponse.toString(), Toast.LENGTH_SHORT).show()

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        departmentResponse.pk_one?.let {
            entries.add(BarEntry(it.toFloat(), 0))
            labels.add("РК-1")
        }

        departmentResponse.pk_two?.let {
            entries.add(BarEntry(it.toFloat(), 1))
            labels.add("РК-2")
        }

        departmentResponse.pk_cp?.let {
            entries.add(BarEntry(it.toFloat(), 2))
            labels.add("РК-СР")
        }

        departmentResponse.final?.let {
            entries.add(BarEntry(it.toFloat(), 3))
            labels.add("Final")
        }

        departmentResponse.total?.let {
            entries.add(BarEntry(it.toFloat(), 4))
            labels.add("Total")
        }

        departmentResponse.retake?.let {
            entries.add(BarEntry(it.toFloat(), 5))
            labels.add("Retake")
        }

        val barDataSet = BarDataSet(entries, "")

        val data = BarData(labels, barDataSet)
        barChart.data = data // set the data and list of lables into chart

        barChart.setDescription("")  // set the description

//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        barDataSet.color = resources.getColor(R.color.colorPrimaryDark)

        barChart.animateY(1000)
    }

    companion object {
        const val TAG = "PostDepartmentFragment"

        @JvmStatic
        fun newInstance(group_id: Int, course_id: Int) =
            PostDepartmentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, group_id)
                    if (course_id != -1) {
                        putInt(ARG_PARAM2, course_id)
                    }
                }
            }
    }
}
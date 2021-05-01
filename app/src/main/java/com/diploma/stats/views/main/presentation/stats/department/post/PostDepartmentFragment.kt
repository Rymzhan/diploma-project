package com.diploma.stats.views.main.presentation.stats.department.post

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.model.department.dep_list.Course
import com.diploma.stats.model.department.dep_list.Department
import com.diploma.stats.model.department.dep_list.Group
import com.diploma.stats.model.department.response.DepartmentResponse
import com.diploma.stats.views.auth.di.AuthScope
import kotlinx.android.synthetic.main.fragment_post_department.*
import kotlinx.android.synthetic.main.fragment_post_department.backToMain
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class PostDepartmentFragment : BaseFragment(), PostDepartmentView {
    private var department: Department? = null
    private var group: Group? = null
    private var course: Course? = null
    private var groupId:Int? = null
    private var departmentId:Int? = null
    private var courseId:Int? = null

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
            department = it.getParcelable(ARG_PARAM1)
            group = it.getParcelable(ARG_PARAM2)
            course = it.getParcelable(ARG_PARAM3)
        }
    }

    override val layoutRes: Int = R.layout.fragment_post_department

    override fun setUp(savedInstanceState: Bundle?) {
        department?.let {
            departmentId = it.id
            departmentValue.text = it.name
            departmentKey.visibility = View.VISIBLE
            departmentValue.visibility = View.VISIBLE
        }
        group?.let {
            if(it.id==-1){
                group = null
            }else{
                groupId = it.id
                groupValue.text = it.name
                groupKey.visibility = View.VISIBLE
                groupValue.visibility = View.VISIBLE
            }
        }
        course?.let {
             if(it.id==-1){
                 course = null
            }else {
                 courseId = it.id
                 courseValue.text = it.name
                 courseKey.visibility = View.VISIBLE
                 courseValue.visibility = View.VISIBLE
             }
        }
        presenter.getCalcByGroup(groupId,courseId,departmentId)
        backToMain.setSafeOnClickListener { requireActivity().onBackPressed() }
    }

    override fun bindResult(departmentResponse: DepartmentResponse) {
//        Toast.makeText(requireContext(), departmentResponse.toString(), Toast.LENGTH_SHORT).show()

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        departmentResponse.pk_one?.let {
            val percentage = it.toInt()
            pbText1.text = "$percentage%"
            progressBar1.progress = percentage
            entries.add(BarEntry(it.toFloat(), 0))
            labels.add("РК-1")
        }

        departmentResponse.pk_two?.let {
            val percentage = it.toInt()
            pbText2.text = "$percentage%"
            progressBar2.progress = percentage
            entries.add(BarEntry(it.toFloat(), 1))
            labels.add("РК-2")
        }

        departmentResponse.pk_cp?.let {
            val percentage = it.toInt()
            pbText3.text = "$percentage%"
            progressBar3.progress = percentage
            entries.add(BarEntry(it.toFloat(), 2))
            labels.add("РК-СР")
        }

        departmentResponse.final?.let {
            val percentage = it.toInt()
            pbText4.text = "$percentage%"
            progressBar4.progress = percentage
            entries.add(BarEntry(it.toFloat(), 3))
            labels.add("Final")
        }

        departmentResponse.total?.let {
            val percentage = it.toInt()
            pbText5.text = "$percentage%"
            progressBar5.progress = percentage
            entries.add(BarEntry(it.toFloat(), 4))
            labels.add("Total")
        }

        departmentResponse.retake?.let {
            val percentage = it.toInt()
            pbText6.text = "$percentage%"
            progressBar6.progress = percentage
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

    override fun bindEmptyView() {
        barChart.visibility = View.GONE
        percentageLayout.visibility = View.GONE
        showMessage("По данному запросу ничего не надо", requireView())
    }

    companion object {
        const val TAG = "PostDepartmentFragment"

        @JvmStatic
        fun newInstance(tempDepartment: Department?, tempGroup: Group?, tempCourse: Course?) =
            PostDepartmentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, tempDepartment)
                    putParcelable(ARG_PARAM2, tempGroup)
                    putParcelable(ARG_PARAM3, tempCourse)
                }
            }
    }
}
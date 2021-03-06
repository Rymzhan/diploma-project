package com.diploma.stats.views.main.presentation.stats.department.pre

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.global.extension.replaceFragmentWithBackStack
import com.diploma.stats.model.department.dep_list.Course
import com.diploma.stats.model.department.dep_list.Department
import com.diploma.stats.model.department.dep_list.Group
import com.diploma.stats.views.scope.di.AuthScope
import com.diploma.stats.views.main.presentation.stats.department.course.StatsByCoursesFragment
import com.diploma.stats.views.main.presentation.stats.department.post.PostDepartmentFragment
import kotlinx.android.synthetic.main.fragment_pre_department.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PreDepartmentFragment : BaseFragment(), PreDepartmentView {

    private var currentDepartment: Department? = null
    private var currentCourse: Course? = null
    private var currentGroup: Group? = null
    private var currentCourseId = -1
    override val layoutRes: Int = R.layout.fragment_pre_department

    @InjectPresenter
    lateinit var presenter: PreDepartmentPresenter

    @ProvidePresenter
    fun providePresenter(): PreDepartmentPresenter {
        return getKoin().getOrCreateScope(AuthScope.PRE_DEP_SCOPE, named(AuthScope.PRE_DEP_SCOPE))
            .get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.PRE_DEP_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.getDepartmentList()
        onClick()
    }

    private fun onClick() {
        finishDepCalcBtn.setSafeOnClickListener {
            makeReplace(currentCourseId)
        }
        backToMain.setSafeOnClickListener { requireActivity().onBackPressed() }

        showStatsByCourse.setSafeOnClickListener {
            requireActivity().supportFragmentManager.replaceFragmentWithBackStack(
                R.id.Container,
                StatsByCoursesFragment.newInstance(),
                StatsByCoursesFragment.TAG
            )
        }
    }

    private fun makeReplace(currentCourseId: Int) {
        requireActivity().supportFragmentManager.replaceFragmentWithBackStack(
            R.id.Container,
            PostDepartmentFragment.newInstance(currentDepartment, currentGroup, currentCourse),
            PostDepartmentFragment.TAG
        )
    }

    override fun bindDepartment(response: MutableList<Department>?) {
        response?.let { departmentList ->

            departmentList.add(0, Department(-1, "???????????????? ??????????????????????????"))
            val adapter = ArrayAdapter(context!!, R.layout.spinner_item, departmentList)

            departmentSpinner.adapter = adapter

            departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    groupSpinner.adapter = null
                    currentDepartment = departmentList[position]
                    if (currentDepartment?.id != -1 && currentDepartment!=null) {
                        finishDepCalcBtn.visibility = View.VISIBLE
                        presenter.getGroupList(currentDepartment?.id!!)
                    }
                }
            }
        }
    }
    override fun bindCourses(response: MutableList<Course>?) {
        response?.let { coursesList ->
            if(coursesList.isEmpty()){

            }else{
                coursesList.add(0, Course(-1, "???????????????? ??????????????"))
                val adapter = ArrayAdapter(context!!, R.layout.spinner_item, coursesList)

                courseSpinner.adapter = adapter

                courseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        currentCourse = coursesList[position]
                        if(currentCourse!=null){
                            when (currentCourse?.id) {
                                -1 -> {
                                    currentCourseId = -1
                                    finishDepCalcBtn.visibility = View.VISIBLE
                                }
                                else -> {
                                    currentCourseId = currentCourse?.id!!
                                    finishDepCalcBtn.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }


        }
    }

    override fun bindGroup(response: MutableList<Group>?) {
        response?.let { groupList ->

            groupList.add(0, Group(-1, -1, "???????????????? ????????????"))
            val adapter = ArrayAdapter(context!!, R.layout.spinner_item, groupList)

            groupSpinner.adapter = adapter

            groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    courseSpinner.adapter = null
                    currentGroup = groupList[position]
                    if (currentGroup?.id != -1 && currentDepartment?.id != -1 && currentDepartment!=null && currentGroup!=null) {
                        presenter.getCoursesList(currentGroup?.id!!)
                    }
                }
            }
        }
    }


    companion object {
        const val TAG = "PreDepartmentFragment"

        fun newInstance() = PreDepartmentFragment()
    }
}
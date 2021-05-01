package com.diploma.stats.views.main.presentation.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diploma.stats.R
import com.diploma.stats.global.extension.replaceFragmentWithBackStack
import com.diploma.stats.views.main.presentation.stats.department.pre.PreDepartmentFragment
import com.diploma.stats.views.main.presentation.stats.student.pre.PreStudentFragment
import kotlinx.android.synthetic.main.fragment_blank.*

class BlankFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statsByAbiturent.setOnClickListener {
            requireActivity().supportFragmentManager.replaceFragmentWithBackStack(
                R.id.Container,
                PreStudentFragment.newInstance(),
                PreStudentFragment.TAG
            )
        }
        statsByDepartment.setOnClickListener {
            requireActivity().supportFragmentManager.replaceFragmentWithBackStack(
                R.id.Container,
                PreDepartmentFragment.newInstance(),
                PreDepartmentFragment.TAG
            )
        }
    }

    companion object {
        const val TAG = "BlankFragment"

        fun newInstance(): BlankFragment{
            return BlankFragment()
        }

    }
}
package com.thousand.bosch.views.main.presentation.game.results.main

import android.os.Bundle
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.views.adapters.PageAdapter
import kotlinx.android.synthetic.main.fragment_results_main.*


class ResultsMainFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.fragment_results_main

    override fun setUp(savedInstanceState: Bundle?) {
        showProgressDialog(true)
        viewPager.adapter = PageAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        showProgressDialog(false)
        backToMain.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    companion object {
        val TAG = "ResultsMainFragment"
        fun newInstance(): ResultsMainFragment {
            return ResultsMainFragment()
        }
    }
}
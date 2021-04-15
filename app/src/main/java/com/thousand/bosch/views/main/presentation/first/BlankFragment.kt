package com.thousand.bosch.views.main.presentation.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thousand.bosch.R
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
        statsByAbiturent.setOnClickListener {  }
        statsByDepartment.setOnClickListener {  }
    }

    companion object {
        const val TAG = "BlankFragment"

        fun newInstance(): BlankFragment{
            return BlankFragment()
        }

    }
}
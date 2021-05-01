package com.diploma.stats.views.main.presentation.stats.student.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.diploma.stats.R
import com.diploma.stats.model.department.dep_list.StudentResponse
import kotlinx.android.synthetic.main.fragment_post_student.*

private const val POST_STUDENT_RESULT = "POST_STUDENT_RESULT"

class PostStudentFragment : Fragment() {
    private var param1: StudentResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(POST_STUDENT_RESULT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToMainStudent.setOnClickListener { requireActivity().onBackPressed() }
        param1?.let {result->
            nameValue.text = "${result.name} ${result.surname}"
            iinValue.text = result.iin

            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()

                entries.add(BarEntry(result.kaz_history.toDouble().toFloat(), 0))
                labels.add("История Казахстана")

                entries.add(BarEntry(result.gr_ct.toDouble().toFloat(), 1))
                labels.add("Грамотность чтения")

                entries.add(BarEntry(result.mathematics.toDouble().toFloat(), 2))
                labels.add("Математическая грамотность")

                entries.add(BarEntry(result.prof_first_point.toDouble().toFloat(), 3))
                labels.add("Проф. предмет 1")

                entries.add(BarEntry(result.prof_second_point.toDouble().toFloat(), 4))
                labels.add("Проф. предмет 2")


            val barDataSet = BarDataSet(entries, "")
            val data = BarData(labels, barDataSet)
            barChartStudent.data = data // set the data and list of lables into chart
            barChartStudent.setDescription("")  // set the description
            barDataSet.color = resources.getColor(R.color.colorPrimaryDark)
            barChartStudent.animateY(1000)

            when(result.ielts_yes.toDouble().toInt()){
                100->{
                    ieltsValue.text = "${result.ielts_point.toDouble()}"
                }
                0->{
                    ieltsValue.text = "Отсутствует"
                }
            }

            when(result.rus.toDouble().toInt()){
                100->{
                    languageValue.text = "Русский"
                }
                0->{
                    languageValue.text = "Казахский"
                }
            }

            pbTextStudent1.text = result.sum_of_points.toDouble().toInt().toString()
            pbTextStudent2.text = result.kaz_history.toDouble().toInt().toString()
            pbTextStudent3.text = result.mathematics.toDouble().toInt().toString()
            pbTextStudent4.text = result.gr_ct.toDouble().toInt().toString()
            pbTextStudent5.text = result.prof_first_point.toDouble().toInt().toString()
            pbTextStudent6.text = result.prof_second_point.toDouble().toInt().toString()

            progressBarStudent1.progress = (result.sum_of_points.toDouble().toInt() * 100)/140
            progressBarStudent2.progress = (result.kaz_history.toDouble().toInt() * 100)/20
            progressBarStudent3.progress = (result.mathematics.toDouble().toInt() * 100)/20
            progressBarStudent4.progress = (result.gr_ct.toDouble().toInt() * 100)/20
            progressBarStudent5.progress = (result.prof_first_point.toDouble().toInt() * 100)/40
            progressBarStudent6.progress = (result.prof_second_point.toDouble().toInt() * 100)/40

        }
    }

    companion object {

        const val TAG = "PostStudentFragment"

        @JvmStatic
        fun newInstance(param1: StudentResponse) =
            PostStudentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(POST_STUDENT_RESULT, param1)
                }
            }
    }
}
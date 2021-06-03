package com.diploma.stats.views.main.presentation.stats.student.pre

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.global.extension.replaceFragmentWithBackStack
import com.diploma.stats.model.department.dep_list.City
import com.diploma.stats.model.department.dep_list.StudentResponse
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.main.presentation.stats.student.city.StatsByCityFragment
import com.diploma.stats.views.main.presentation.stats.student.post.PostStudentFragment
import kotlinx.android.synthetic.main.fragment_pre_student.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PreStudentFragment : BaseFragment(), PreStudentView {

    private var cityId = -1

    override val layoutRes: Int = R.layout.fragment_pre_student

    @InjectPresenter
    lateinit var presenter: PreStudentPresenter

    @ProvidePresenter
    fun providePresenter(): PreStudentPresenter {
        return getKoin().getOrCreateScope(AuthScope.PRE_STUDENT_SCOPE, named(AuthScope.PRE_STUDENT_SCOPE))
            .get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.PRE_STUDENT_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.getCitiesList()

        backToMain.setSafeOnClickListener { requireActivity().onBackPressed() }

        showStatsByCity.setSafeOnClickListener {
            closeKeyboard(requireActivity())
            requireActivity().supportFragmentManager.replaceFragmentWithBackStack(
                R.id.Container,
                StatsByCityFragment.newInstance(),
                StatsByCityFragment.TAG
            )
        }

        calcByIINBtn.setSafeOnClickListener {
            when(iinEditText.text.toString().length){
                12->{
                    val iin: Long = iinEditText.text.toString().trim().toLong()
                    presenter.getStudentResultByIIN(iin,null,null,null)
                }
                else->{
                    iinEditText.error = "Заполните данное поле!"
                }
            }
        }

        calcByCityBtn.setSafeOnClickListener {
            when{
                cityId==-1->{
                    Toast.makeText(requireContext(),"Выберите город!", Toast.LENGTH_LONG).show()
                }
                firstNameEditText.text.toString().isEmpty()->{
                    firstNameEditText.error = "Заполните данное поле!"
                }
                lastNameEditText.text.toString().isEmpty()->{
                    lastNameEditText.error = "Заполните данное поле!"
                }
                else->{
                    presenter.getStudentResult(null,cityId,lastNameEditText.text.toString(),firstNameEditText.text.toString())
                }
            }
        }

        corellationBtn.setSafeOnClickListener {
            presenter.getCorellation()
        }
    }

    override fun bindCitiesList(cities: MutableList<City>?) {
        cities?.let { citiesList ->

            citiesList.add(0, City(-1, "Выберите город"))
            val adapter = ArrayAdapter(context!!, R.layout.spinner_item, citiesList)

            city_spinner.adapter = adapter

            city_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cityId = citiesList[position].id
                }
            }
        }
    }

    override fun showEmptyResult() {
        closeKeyboard(requireActivity())
        showMessage("По данному запросу ничего не найдено!",requireView())
    }

    override fun bindToNextPage(studentResponse: StudentResponse) {
        closeKeyboard(requireActivity())
        requireActivity().supportFragmentManager.replaceFragmentWithBackStack(
            R.id.Container,
            PostStudentFragment.newInstance(studentResponse),
            PostStudentFragment.TAG
        )
    }

    override fun showCorellation(response: Double?) {
        response?.let {
            closeKeyboard(requireActivity())
            SweetAlertDialog(requireActivity(),SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Корелляция успеваемости всех студентов:")
                .setContentText("$it%")
                .show()
        }
    }

    override fun showError(message: String?) {
        message?.let {
            Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val TAG = "PreStudentFragment"

        @JvmStatic
        fun newInstance() = PreStudentFragment()

    }
}
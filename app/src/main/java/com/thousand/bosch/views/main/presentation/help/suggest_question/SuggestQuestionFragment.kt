package com.thousand.bosch.views.main.presentation.help.suggest_question

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.model.main.game.categories.RandomCategories
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import kotlinx.android.synthetic.main.fragment_suggest_question.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class SuggestQuestionFragment : BaseFragment(), SuggestQuestionView {
    override val layoutRes: Int = R.layout.fragment_suggest_question

    private val nameList: MutableList<String> = mutableListOf()
    private val idList: MutableList<Int> = mutableListOf()

    private var catId: Int? = -1

    @InjectPresenter
    lateinit var presenter: SuggestQuestionPresenter

    companion object {
        val TAG = "SuggestQuestionFragment"
        fun newInstance(): SuggestQuestionFragment {
            return SuggestQuestionFragment()
        }
    }

    @ProvidePresenter
    fun providePresenter(): SuggestQuestionPresenter {
        getKoin().getScopeOrNull(AuthScope.SUGGEST_QUESTION)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.SUGGEST_QUESTION,
            named(AuthScope.SUGGEST_QUESTION)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.SUGGEST_QUESTION)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.getCategoryList()
        fromSettingsToProfile.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }/*
        suggestDescriptionText.setScroller(Scroller(requireContext()))
        suggestDescriptionText.isVerticalScrollBarEnabled = true
        suggestDescriptionText.movementMethod = ScrollingMovementMethod()*/
    }

    override fun bindCategories(list: RandomCategories?) {
        if (!list.isNullOrEmpty()) {
            nameList.clear()
            nameList.add("Выберите категорию")
            for (i in list.indices) {
                nameList.add(list[i].title)
                idList.add(list[i].id)
            }

            val spinner: Spinner = categorySpinner

            val adapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_item,
                nameList
            )

            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        catId = idList[position - 1]
                    }
                    if (position < 1) {
                        catId = -1
                    }
                }
            }

            suggestButton.setSafeOnClickListener {
                val email = suggestEmailText.text.toString()
                val description = suggestDescriptionText.text.toString()
                if (email.isEmpty()) {
                    suggestEmailText.error = "Заполните это поле!"
                }
                if (description.isEmpty()) {
                    suggestDescriptionText.error = "Заполните это поле!"
                }
                if (catId == -1) {
                    showMessage("Выберите категорию!", requireView())
                }
                if (email.isNotEmpty() && description.isNotEmpty() && catId != -1) {
                    showProgress(requireContext())
                    presenter.suggestQuestion(email, catId!!, description)
                }
            }

        }
    }

    override fun openProfileFrag() {
        removeProgress()
        requireActivity().supportFragmentManager.replaceFragment(
            R.id.Container,
            ProfileFragment.newInstance(),
            ProfileFragment.TAG
        )
    }


}
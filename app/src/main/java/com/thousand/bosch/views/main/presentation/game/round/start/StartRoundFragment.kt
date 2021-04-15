package com.thousand.bosch.views.main.presentation.game.round.start

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.global.extension.setImageUrl
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.main.game.round.start.Answer
import com.thousand.bosch.model.main.game.round.start.Question
import com.thousand.bosch.views.adapters.AnswersAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.game.category.second.CategoryConfirmFragment
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import kotlinx.android.synthetic.main.fragment_category_confirm.*
import kotlinx.android.synthetic.main.fragment_start_round.*
import kotlinx.android.synthetic.main.fragment_start_round.backToMain
import kotlinx.android.synthetic.main.fragment_start_round.imageView24
import kotlinx.android.synthetic.main.fragment_start_round.imageView39
import kotlinx.android.synthetic.main.fragment_start_round.imageView40
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named


class StartRoundFragment() : BaseFragment(), StartRoundView {

    private var cb: OnBackPressedCallback? = null
    private var timer: CountDownTimer? = null
    private var roundId = 0
    private var firstQuestion: Question? = null
    private var secondQuestion: Question? = null
    private var thirdQuestion: Question? = null
    private var runnable: Runnable? = null
    private var handler: Handler? = null
    private var answerList: MutableList<Int> = mutableListOf()
    private var correctList: MutableList<Int> = mutableListOf()
    private var runnable2: Runnable? = null
    private var handler2: Handler? = null
    private var fullScreen: Boolean = false

    private var answersAdapter = AnswersAdapter(object : AnswersAdapter.OnSelectedListener {
        override fun onSelected(answerId: Int, isCorrect: Int) {
            answerList.add(answerId)
            correctList.add(isCorrect)
            try {
                timer!!.cancel()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            bindDots()
        }
    })

    @InjectPresenter
    lateinit var presenter: StartRoundPresenter

    @ProvidePresenter
    fun providePresenter(): StartRoundPresenter {
        getKoin().getScopeOrNull(AuthScope.START_ROUND_SCOPE)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.START_ROUND_SCOPE,
            named(AuthScope.START_ROUND_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        try {
            timer!!.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getKoin().getScopeOrNull(AuthScope.START_ROUND_SCOPE)?.close()
        super.onDestroy()
    }

    override val layoutRes: Int = R.layout.fragment_start_round

    override fun setUp(savedInstanceState: Bundle?) {
        roundNumberText.text = arguments?.getInt(currentR)!!.toString()
        firstQuestion = arguments?.getParcelable(fQ)!!
        secondQuestion = arguments?.getParcelable(sQ)!!
        thirdQuestion = arguments?.getParcelable(tQ)!!
        roundId = arguments?.getInt(rId)!!
        roundCountText.text = "Вопрос 1/3"

        val img: ImageView = questionImage
        img.setImageUrl(arguments?.getString(CategoryConfirmFragment.categoryImg))
        img.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            override fun onGlobalLayout() {
                val drawable = ContextCompat.getDrawable(context!!, R.drawable.bottom_tint)
                drawable!!.setBounds(0, 0, img.width, img.height)

                val drawable2 = ContextCompat.getDrawable(context!!, R.drawable.top_tint)
                drawable2!!.setBounds(0, 0, img.width, img.height)
                img.overlay.add(drawable)
                img.overlay.add(drawable2)
                img.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        )

        if (firstQuestion!!.image != null) {
            Glide.with(requireContext())
                .load(firstQuestion!!.image)
                .centerCrop()
                .into(img)
        }

        val numberOfColumns = 2
        val gridLayoutManager =
            GridLayoutManager(activity, numberOfColumns, RecyclerView.VERTICAL, false)
        answersRecycler.layoutManager = gridLayoutManager
        answersRecycler.setHasFixedSize(true)
        answersAdapter.addContext(activity!!)
        questionText.text = firstQuestion!!.title
        categoryText.text = arguments!!.getString(categoryTitle)
        answersAdapter.setDataList(firstQuestion!!.answers as MutableList<Answer>)
        answersAdapter.notifyDataSetChanged()
        answersRecycler.adapter = answersAdapter
        handler = Handler()
        handler!!.postDelayed({
            removeProgress()
            setTimer()
        }, 100)

        cb = (object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Покинуть раунд")
                    builder.setMessage("Вы проиграете, если покинете раунд" + "\n" + "Покинуть раунд?")

                    builder.setPositiveButton("Да") { dialog, _ ->
                        if (isEnabled) {
                            isEnabled = false
                        }

                        presenter.loseRound(roundId)
                        dialog.dismiss()
                    }

                    builder.setNegativeButton(
                        "Нет",
                        DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })

                    val alert = builder.create()
                    alert.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        apply { activity!!.onBackPressedDispatcher.addCallback(cb!!) }

        backToMain.setSafeOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        val TAG = "StartRoundFragment"
        const val rId = "guestImage"
        const val fQ = "fQ"
        const val sQ = "sQ"
        const val tQ = "tQ"
        const val categoryTitle = "categoryTitle"
        const val currentR = "currentR"
        fun newInstance(
            roundId1: Int?,
            firstQuestion: Question,
            secondQuestion: Question,
            thirdQuestion: Question,
            catTitle: String?,
            curRound: Int
        ): StartRoundFragment {
            val fragment =
                StartRoundFragment()
            val args = Bundle()
            args.putInt(rId, roundId1!!)
            args.putParcelable(fQ, firstQuestion)
            args.putParcelable(sQ, secondQuestion)
            args.putParcelable(tQ, thirdQuestion)
            args.putString(categoryTitle, catTitle)
            args.putInt(currentR, curRound)
            fragment.arguments = args
            return fragment
        }
    }

    override fun startRound(
        id: Int,
        firstQuestion: Question,
        secondQuestion: Question,
        thirdQuestion: Question
    ) {
        roundCountText.text = "Вопрос 1/3"
        if (firstQuestion.image != null) {
            Glide.with(requireContext())
                .load(firstQuestion.image)
                .circleCrop()
                .into(questionImage)
        }
        roundId = id
        this.firstQuestion = firstQuestion
        this.secondQuestion = secondQuestion
        this.thirdQuestion = thirdQuestion
        questionText.text = firstQuestion.title
        categoryText.text = arguments!!.getString(categoryTitle)
        answersAdapter.setDataList(firstQuestion.answers as MutableList<Answer>)
        answersAdapter.notifyDataSetChanged()

    }

    private fun setTimer() {
        try {
            timer!!.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val progressBar = progressBarHorizontal
        progressBar.progress = 100
        timer = object : CountDownTimer(15000, 150) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished / 150).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                showProgress(context!!)
                progressBar.progress = 0
                handler = Handler()
                handler!!.postDelayed({
                    removeProgress()
                    setTimer2()
                }, 0)
            }
        }
        timer!!.start()

    }

    private fun setTimer2() {
        timer!!.cancel()
        roundCountText.text = "Вопрос 2/3"
        if (secondQuestion!!.image != null) {
            Glide.with(requireContext())
                .load(secondQuestion!!.image)
                .circleCrop()
                .into(questionImage)
        }
        questionText.text = secondQuestion!!.title
        answersAdapter.setBool(true)
        answersAdapter.setDataList(secondQuestion!!.answers as MutableList<Answer>)
        answersAdapter.notifyDataSetChanged()

        if (answerList.size == 0) {
            if (correctList.size == 0) {
                correctList.add(0)
                apply {
                    imageView24.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_red_circle
                        )
                    )
                }
            }
            for (i in firstQuestion!!.answers.indices) {
                if (firstQuestion!!.answers[i].is_correct == 0) {
                    answerList.add(firstQuestion!!.answers[i].id)
                    break
                }
            }
        }

        val progressBar = progressBarHorizontal
        progressBar.progress = 100
        timer = object : CountDownTimer(15000, 150) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished / 150).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                apply { showProgress(context!!) }
                progressBar.progress = 0
                if (correctList.size == 1) {
                    correctList.add(0)
                }
                if (correctList[1] == 0) {
                    apply {
                        imageView39.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity!!,
                                R.drawable.ic_red_circle
                            )
                        )
                    }
                }

                handler = Handler()
                handler!!.postDelayed({
                    setTimer3()
                    removeProgress()
                }, 0)
            }
        }
        handler = Handler()
        handler!!.postDelayed({
            removeProgress()
            timer!!.start()
        }, 0)

    }

    private fun setTimer3() {
        timer!!.cancel()
        roundCountText.text = "Вопрос 3/3"
        if (thirdQuestion!!.image != null) {
            Glide.with(requireContext())
                .load(thirdQuestion!!.image)
                .circleCrop()
                .into(questionImage)
        }
        questionText.text = thirdQuestion!!.title
        answersAdapter.setBool(true)
        answersAdapter.setDataList(thirdQuestion!!.answers as MutableList<Answer>)
        answersAdapter.notifyDataSetChanged()

        if (answerList.size == 1) {
            for (i in secondQuestion!!.answers.indices) {
                if (secondQuestion!!.answers[i].is_correct == 0) {
                    answerList.add(secondQuestion!!.answers[i].id)
                    break
                }
            }
        }

        val progressBar = progressBarHorizontal
        progressBar.progress = 100
        timer = object : CountDownTimer(15000, 150) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished / 150).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                progressBar.progress = 0
                apply { showProgress(context!!) }
                handler = Handler()
                handler!!.postDelayed({
                    presenter.answerToRound(
                        roundId,
                        firstQuestion!!.id,
                        answerList[0],
                        secondQuestion!!.id,
                        answerList[1],
                        thirdQuestion!!.id,
                        answerList[2]
                    )
                    removeProgress()
                }, 200)
                if (answerList.size == 2) {
                    for (i in thirdQuestion!!.answers.indices) {
                        if (thirdQuestion!!.answers[i].is_correct == 0) {
                            answerList.add(thirdQuestion!!.answers[i].id)
                            break
                        }
                    }
                    if (correctList.size == 2) {
                        correctList.add(0)
                    }
                }

                if (correctList[2] == 0) {
                    apply {
                        imageView40.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity!!,
                                R.drawable.ic_red_circle
                            )
                        )
                    }
                }

            }
        }
        handler = Handler()
        handler!!.postDelayed({
            removeProgress()
            timer!!.start()
        }, 0)

    }

    override fun openResultsPage() {
        removeProgress()
        try {
            cb!!.isEnabled = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        showProgress(context!!)
        activity?.supportFragmentManager?.replaceFragment(
            R.id.Container,
            ProfileFragment.newInstance(),
            ProfileFragment.TAG
        )
    }

    override fun showError() {
        showMessage("Ошибка соединения!", requireView())
    }

    private fun bindAdapter() {
        timer!!.cancel()
        if (answerList.size == 1) {
            setTimer2()
        }
        if (answerList.size == 2) {
            setTimer3()
        }
        if (answerList.size == 3) {
            presenter.answerToRound(
                roundId,
                firstQuestion!!.id,
                answerList[0],
                secondQuestion!!.id,
                answerList[1],
                thirdQuestion!!.id,
                answerList[2]
            )
        } else {
            Log.d("size", "isNull")
        }
    }

    fun bindDots() {
        try {
            progressBarHorizontal.visibility = View.GONE
            continueAnswerButton.visibility = View.VISIBLE
            continueAnswerButton.setSafeOnClickListener {
                showProgress(context!!)
                handler = Handler()
                handler!!.postDelayed({
                    bindAdapter()
                    progressBarHorizontal.visibility = View.VISIBLE
                    continueAnswerButton.visibility = View.GONE
                }, 0)
            }
            if (answerList.size == 1) {
                if (correctList[0] == 1) {
                    imageView24.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_green_circle
                        )
                    )
                } else if (correctList[0] == 0) {
                    imageView24.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_red_circle
                        )
                    )
                }
            }
            if (answerList.size == 2) {
                if (correctList[1] == 1) {
                    imageView39.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_green_circle
                        )
                    )
                } else if (correctList[1] == 0) {
                    imageView39.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_red_circle
                        )
                    )
                }
            }
            if (answerList.size == 3) {
                if (correctList[2] == 1) {
                    imageView40.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_green_circle
                        )
                    )
                } else if (correctList[2] == 0) {
                    imageView40.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            R.drawable.ic_red_circle
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
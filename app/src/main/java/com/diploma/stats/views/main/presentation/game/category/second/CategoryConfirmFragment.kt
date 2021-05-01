package com.diploma.stats.views.main.presentation.game.category.second

import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.diploma.stats.R
import com.diploma.stats.global.extension.replaceFragmentWithBackStack
import com.diploma.stats.global.extension.setImageUrl
import com.diploma.stats.model.main.game.round.start.Question
import com.diploma.stats.model.main.game.round.start.RoundStartModel
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.main.presentation.game.round.start.StartRoundFragment
import com.diploma.stats.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_category_confirm.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class CategoryConfirmFragment : BaseFragment(), CategoryConfirmView {
    private var roundId = 0
    private var firstQuestion: Question? = null
    private var secondQuestion: Question? = null
    private var thirdQuestion: Question? = null

    @InjectPresenter
    lateinit var presenter: CategoryConfirmPresenter

    @ProvidePresenter
    fun providePresenter(): CategoryConfirmPresenter {
        getKoin().getScopeOrNull(AuthScope.CATEGORIES_CONFIRM)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.CATEGORIES_CONFIRM,
            named(AuthScope.CATEGORIES_CONFIRM)
        ).get()
    }

    override fun onDestroy() {
        try {
            enableClick!!.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        getKoin().getScopeOrNull(AuthScope.CATEGORIES_CONFIRM)?.close()
        super.onDestroy()
    }

    /*
    * var refreshData: (() -> Unit)? = null
        fun newInstance(loadData: () -> Unit): FriendsListFragment {
            refreshData = loadData
            return FriendsListFragment()
        }
        * */
    companion object {
        val TAG = "CategoryConfirmFragment"
        const val categoryId = "guestImage"
        const val gameId = "gameId"
        const val categoryImg = "categoryImg"
        const val categoryTitle = "categoryTitle"
        private const val friendId = "friendId"
        const val currentR = "currRound"
        var enableClick: (() -> Unit)? = null
        fun newInstance(
            enable: () -> Unit,
            gameId1: Int?,
            categoryId1: Int?,
            categoryImg1: String?,
            categoryTitle1: String?,
            guestId: Int?,
            curRound: Int
        ): CategoryConfirmFragment {
            enableClick = enable
            val fragment =
                CategoryConfirmFragment()
            val args = Bundle()
            args.putInt(categoryId, categoryId1!!)
            args.putInt(gameId, gameId1!!)
            args.putString(categoryTitle, categoryTitle1)
            args.putString(categoryImg, categoryImg1)
            args.putInt(friendId, guestId!!)
            args.putInt(currentR, curRound)
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutRes: Int = R.layout.fragment_category_confirm

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun setUp(savedInstanceState: Bundle?) {
        removeProgress()
        roundNumberCategoryConfirm.text = arguments?.getInt(currentR)!!.toString()
        categoryConfirmTitle.text = arguments?.getString(categoryTitle)
        val img: ImageView = categoryConfirmedImage
        img.setImageUrl(arguments?.getString(categoryImg))
        img.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val drawable = ContextCompat.getDrawable(context!!, R.drawable.bottom_tint)
                drawable!!.setBounds(0, 0, img.width, img.height)
                img.overlay.add(drawable)
                img.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        )

        startRoundButton.setSafeOnClickListener {
            showProgress(context!!)
            imageConfirmButton.isClickable = false
            presenter.startRound(arguments?.getInt(gameId)!!, arguments?.getInt(categoryId)!!)
        }

        imageConfirmButton.setSafeOnClickListener {
            showProgress(context!!)
            startRoundButton.isClickable = false
            presenter.startRound(arguments?.getInt(gameId)!!, arguments?.getInt(categoryId)!!)
        }

        backToMain.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun startRound(obj: RoundStartModel) {
        firstQuestion = obj.data.questions[0]
        secondQuestion = obj.data.questions[1]
        thirdQuestion = obj.data.questions[2]
        roundId = obj.data.id
        activity?.supportFragmentManager?.replaceFragmentWithBackStack(
            R.id.category_confirm_page,
            StartRoundFragment.newInstance(
                roundId,
                firstQuestion!!,
                secondQuestion!!,
                thirdQuestion!!,
                arguments?.getString(categoryTitle)!!,
                arguments?.getInt(currentR)!!
            ),
            StartRoundFragment.TAG
        )
    }

    override fun showError() {
        removeProgress()
        showProgressDialog(false)
        imageConfirmButton.isClickable = true
        startRoundButton.isClickable = true
        Toast.makeText(requireContext(), "Ошибка соединения!", Toast.LENGTH_SHORT).show()
    }


}
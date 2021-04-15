package com.thousand.bosch.views.main.presentation.game.results.friend

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.game.round.friend.ResultsFriendFragment
import com.thousand.bosch.views.main.presentation.profile.details.ProfileDetailsFragment
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import kotlinx.android.synthetic.main.fragment_game_friend_result.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class GameFriendResultFragment : BaseFragment(), GameFriendView {
    override val layoutRes: Int = R.layout.fragment_game_friend_result
    private var handler: Handler? = null

    @InjectPresenter
    lateinit var presenter: GameFriendPresenter

    @ProvidePresenter
    fun providePresenter(): GameFriendPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.GAME_FRIEND_SCOPE,
            named(AuthScope.GAME_FRIEND_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.GAME_FRIEND_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        bindView()
        onClick()
        if (arguments?.getInt(winner) == LocalStorage.getID()) {
            winText.text = "Поздравляем, вы выиграли!"
            viewKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.RED, Color.CYAN)
                .setDirection(0.0, 359.0)
                .setSpeed(12f, 12f)
                .setFadeOutEnabled(true)
                .setTimeToLive(1000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(8))
                .setPosition(0f, viewKonfetti.width + 1550f, -50f, -50f)
                .streamFor(400, 1000L)
        }
        if (arguments?.getInt(winner) == 0) {
            winText.text = "Вы сыграли вничью."
        } else if (arguments?.getInt(winner) != LocalStorage.getID() && arguments?.getInt(winner) != 0) {
            winText.text = "Вы потерпели поражение."
        }
    }

    private fun onClick() {
        backToMain.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }

        mainMenuButton.setSafeOnClickListener {
            requireActivity().supportFragmentManager.replaceFragment(
                R.id.Container,
                ProfileFragment.newInstance(),
                ProfileFragment.TAG
            )
        }

        watchStatisticsButton.setSafeOnClickListener {
            requireActivity().supportFragmentManager.addFragmentWithBackStack(
                R.id.Container,
                ProfileDetailsFragment.newInstance(),
                ProfileDetailsFragment.TAG
            )
        }

        revengeButton.setSafeOnClickListener {
            requireActivity().supportFragmentManager.addFragmentWithBackStack(
                R.id.Container,
                ResultsFriendFragment.newInstance(arguments?.getInt(guest), 0),
                ResultsFriendFragment.TAG
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindView() {
        if (LocalStorage.getImage() != "1") {
            Glide.with(requireContext())
                .load(LocalStorage.getImage())
                .circleCrop()
                .into(creatorAvatar)
        }
        if (arguments?.getString(guestI) != null) {
            Glide.with(requireContext())
                .load(arguments?.getString(guestI))
                .circleCrop()
                .into(guestAvatar)
        }
        creatorName.text = LocalStorage.getName() + " " + LocalStorage.getSurname()
        guestName.text = arguments?.getString(guestN)
        userPoints.text =
            arguments?.getInt(userPoints1).toString() + " - " + arguments?.getInt(guestPoints1)
                .toString()

    }

    companion object {
        val TAG = "GameFriendResultFragment"
        private const val guest = "guestId"
        private const val winner = "winnerId"
        private const val guestN = "guestName"
        private const val guestI = "guestImage"
        private const val userPoints1 = "userPoints"
        private const val guestPoints1 = "guestPoints"
        fun newInstance(
            guestId: Int,
            winnerId: Int?,
            guestName: String,
            guestImage: String?,
            userPoint: Int,
            guestPoint: Int
        ): GameFriendResultFragment {
            val fragment =
                GameFriendResultFragment()
            val args = Bundle()
            if (winnerId != null) {
                args.putInt(guest, guestId)
                args.putString(guestN, guestName)
                args.putString(guestI, guestImage)
                args.putInt(winner, winnerId)
                args.putInt(userPoints1, userPoint)
                args.putInt(guestPoints1, guestPoint)
            } else {
                args.putInt(guest, guestId)
                args.putString(guestN, guestName)
                args.putString(guestI, guestImage)
                args.putInt(winner, 0)
                args.putInt(userPoints1, userPoint)
                args.putInt(guestPoints1, guestPoint)
            }
            fragment.arguments = args
            return fragment
        }
    }

}
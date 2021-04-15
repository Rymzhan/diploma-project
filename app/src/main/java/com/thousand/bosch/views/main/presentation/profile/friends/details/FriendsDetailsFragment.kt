package com.thousand.bosch.views.main.presentation.profile.friends.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.model.main.profile.statistics.CategoryStat
import com.thousand.bosch.model.main.profile.statistics.Stats
import com.thousand.bosch.model.main.search.SearchUser
import com.thousand.bosch.views.adapters.CategoriesProfileAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.game.round.friend.ResultsFriendFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.thousand.bosch.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_freinds_details.*
import kotlinx.android.synthetic.main.fragment_freinds_details.averagePoints
import kotlinx.android.synthetic.main.fragment_freinds_details.drawPercentage
import kotlinx.android.synthetic.main.fragment_freinds_details.gamesCount
import kotlinx.android.synthetic.main.fragment_freinds_details.losePercentage
import kotlinx.android.synthetic.main.fragment_freinds_details.placeNumber
import kotlinx.android.synthetic.main.fragment_freinds_details.winsPercentage
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.lang.Exception


class FriendsDetailsFragment() : BaseFragment(), FriendsDetailsView {

    override val layoutRes: Int = R.layout.fragment_freinds_details
    var checkBool: Boolean = false
    var ifFriend: Boolean? = null

    @InjectPresenter
    lateinit var presenter: FriendsDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): FriendsDetailsPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.FRIEND_DETAILS_SCOPE,
            named(AuthScope.FRIEND_DETAILS_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        apply {
            if (checkBool) {
                refreshData!!.invoke()
            }
        }
        getKoin().getScopeOrNull(AuthScope.FRIEND_DETAILS_SCOPE)?.close()
        super.onDestroy()
    }

    companion object {
        val TAG = "FriendsDetailsFragment"
        const val userId = "userId"
        const val userInfo = "user"
        const val isFriend = "isFriend"
        var refreshData: (() -> Unit)? = null
        fun newInstance(
            id: Int?,
            user: SearchUser?,
            inFriends: Boolean?,
            loadData: () -> Unit
        ): FriendsDetailsFragment {
            val fragment = FriendsDetailsFragment()
            refreshData = loadData
            val args = Bundle()
            args.putInt(userId, id!!)
            args.putParcelable(userInfo, user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun setUp(savedInstanceState: Bundle?) {
        val pieChart = userPieChart as PieChart
        pieChart.setNoDataText("")
        showProgressBar(true)
        showProgress(context!!)
        presenter.getUserStats(arguments?.getInt(userId)!!)
        onClick()

    }


    private fun onClick() {
        showProgressBar(false)
        backToMainFromFriend.setOnClickListener {
            activity?.onBackPressed()
        }

        blockFriendUserButton.setSafeOnClickListener {
            dialog(
                "Заблокировать пользователя",
                "Вы действительно хотите заблокировать данного пользователя?",
                1
            )
        }

        deleteFriendButton.setSafeOnClickListener {
            dialog("Удалить друга", "Вы действительно хотите удалить данного пользователя?", 2)
        }

        unBlockButton.setSafeOnClickListener {
            dialog(
                "Разблокировать пользователя",
                "Вы действительно хотите разблокировать данного пользователя?",
                4
            )
        }

        addToFriendButton.setSafeOnClickListener {
            dialog("Добавить в друзья", "Вы действительно хотите добавить данного пользователя?", 3)
        }

        inviteFriendUserButton.setSafeOnClickListener {
            showProgress(context!!)
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.friend_details,
                ResultsFriendFragment.newInstance(arguments?.getInt(userId)!!, 0),
                ResultsFriendFragment.TAG
            )
        }
    }

    fun dialog(title: String, message: String, int: Int) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton("Да") { dialog, _ ->
            when (int) {
                3 -> {
                    presenter.add_friend(arguments?.getInt(userId)!!)
                }
                2 -> {
                    presenter.delete_user(
                        arguments?.getInt(userId)!!
                    )
                }
                1 -> {
                    presenter.blockUser(
                        arguments?.getInt(userId)!!
                    )
                }
                4 -> {
                    presenter.unBlock(arguments?.getInt(userId)!!)
                }
            }

            dialog.dismiss()
        }

        builder.setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alert = builder.create()
        alert.show()
    }

    override fun bindFriendInfo(friend: DataX) {
        commonBind(
            friend.scores,
            friend.image,
            friend.first_name + " " + friend.last_name,
            friend.login,
            friend.in_friends,
            friend.in_blacklist
        )
    }

    override fun bindFriendInfoAfterDelete(friend: DataX) {
        checkBool = true
        commonBind(
            friend.scores,
            friend.image,
            friend.first_name + " " + friend.last_name,
            friend.login,
            friend.in_friends,
            friend.in_blacklist
        )
    }

    override fun goToList() {
        activity?.onBackPressed()
    }

    private fun bindChart(stats: Stats) {
        val pieChart = userPieChart as PieChart
        pieChart.setNoDataText("")
        if (stats.finished_games_count == 0) {
            gamesHintText2.visibility = View.GONE
            gamesCount.visibility = View.GONE
            pieChart.visibility = View.GONE
            friendGamesEmptyText.visibility = View.VISIBLE
        } else {
            gamesHintText2.visibility = View.VISIBLE
            gamesCount.visibility = View.VISIBLE
            pieChart.visibility = View.VISIBLE
            friendGamesEmptyText.visibility = View.GONE
        }

        winsPercentage.text = stats.wins_percentage.toString() + "%"
        drawPercentage.text = stats.draws_percentage.toString() + "%"
        losePercentage.text = stats.loses_percentage.toString() + "%"
        averagePoints.text = stats.points_average.toString()
        placeNumber.text = stats.rating.toString()
        gamesCount.text = stats.finished_games_count.toString()

        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false

        val listPie = ArrayList<PieEntry>()
        val listColors = ArrayList<Int>()
        listPie.add(PieEntry(stats.wins_percentage.toFloat(), ""))
        listColors.add(resources.getColor(R.color.green))
        listPie.add(PieEntry(stats.loses_percentage.toFloat(), ""))
        listColors.add(resources.getColor(R.color.red))
        listPie.add(PieEntry(stats.draws_percentage.toFloat(), ""))
        listColors.add(resources.getColor(R.color.colorPrimary))

        val pieDataSet = PieDataSet(listPie, "")
        pieDataSet.colors = listColors
        pieDataSet.setDrawValues(false)
        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(14f)
        pieChart.data = pieData

        pieChart.setUsePercentValues(true)
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(resources.getColor(R.color.backgroundGrey))
        pieChart.holeRadius = 80f
        pieChart.transparentCircleRadius = 5f

        pieChart.setDrawMarkerViews(false)
        pieChart.setDrawSliceText(false)
        pieChart.setDrawMarkers(false)
        pieChart.setDrawEntryLabels(false)

        pieChart.animateY(1400, Easing.EaseInOutQuad)

    }

    override fun bindUserInfo() {
        try {
            val currentUser: SearchUser = arguments?.getParcelable(userInfo)!!
            commonBind(
                currentUser.scores,
                currentUser.image,
                currentUser.first_name + " " + currentUser.last_name,
                currentUser.login,
                currentUser.in_friends,
                currentUser.in_blacklist
            )
        } catch (e: Exception) {
            presenter.getFriendInfo(arguments?.getInt(userId)!!)
            e.printStackTrace()
        }
    }

    override fun bindStats(stats: Stats, categoryStats: List<CategoryStat>) {
        val categoriesRecycler = friendCategoriesRecycler as RecyclerView
        if (categoryStats.isNullOrEmpty()) {
            categoriesRecycler.visibility = View.GONE
            friendCatsEmptyText.visibility = View.VISIBLE
        } else {
            categoriesRecycler.visibility = View.VISIBLE
            friendCatsEmptyText.visibility = View.GONE
        }
        bindChart(stats)

        categoriesRecycler.layoutManager = LinearLayoutManager(context)
        categoriesRecycler.adapter =
            CategoriesProfileAdapter(categoryStats, context!!)

        presenter.getFriendInfo(arguments?.getInt(userId)!!)

    }

    private fun commonBind(
        scores: Int,
        image: String?,
        name: String,
        login: String,
        inFriends: Boolean,
        inBlock: Boolean
    ) {
        ifFriend = inFriends
        if (!inFriends) {
            addToFriendButton.visibility = View.VISIBLE
            deleteFriendButton.visibility = View.GONE
        }
        if (inFriends) {
            addToFriendButton.visibility = View.GONE
            deleteFriendButton.visibility = View.VISIBLE
        }
        if (image != null) {
            Glide.with(context!!)
                .load(image)
                .circleCrop()
                .into(friendDetailsImage)
        }
        friendDetailsScore.setText(scores.toString() + " баллов")
        friendDetailsName.setText(name)
        friendDetailsLogin.setText("@" + login)
        if (inBlock) {
            blockFriendUserButton.visibility = View.INVISIBLE
            unBlockButton.visibility = View.VISIBLE
        }
        if (!inBlock) {
            unBlockButton.visibility = View.INVISIBLE
            blockFriendUserButton.visibility = View.VISIBLE
        }
        removeProgress()
    }


}
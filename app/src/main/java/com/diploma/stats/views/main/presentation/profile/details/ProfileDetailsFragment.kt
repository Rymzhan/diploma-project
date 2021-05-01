package com.diploma.stats.views.main.presentation.profile.details

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.diploma.stats.R
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.profile.statistics.CategoryStat
import com.diploma.stats.model.main.profile.statistics.Stats
import com.diploma.stats.views.adapters.CategoriesProfileAdapter
import com.diploma.stats.views.adapters.FriendsListAdapter
import com.diploma.stats.views.auth.di.AuthScope
import com.diploma.stats.views.main.presentation.help.HelpFragment
import com.diploma.stats.views.main.presentation.profile.edit.ProfileEditFragment
import com.diploma.stats.views.main.presentation.profile.friends.list.FriendsListFragment
import com.diploma.stats.views.main.presentation.profile.settings.SettingsFragment
import com.github.mikephil.charting.charts.PieChart
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.global.extension.addFragmentWithBackStack
import kotlinx.android.synthetic.main.fragment_profile_details.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.util.*


class ProfileDetailsFragment() : BaseFragment(), ProfileDetailsView {
    override val layoutRes: Int = R.layout.fragment_profile_details
    private lateinit var random: Random
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var user: User? = null

    companion object {
        val TAG = "ProfileDetailsFragment"
        fun newInstance(): ProfileDetailsFragment {
            return ProfileDetailsFragment()
        }
    }

    @InjectPresenter
    lateinit var presenter: ProfileDetailsPresenter

    var tempInstance: Bundle? = null

    @ProvidePresenter
    fun providePresenter(): ProfileDetailsPresenter {
        getKoin().getScopeOrNull(AuthScope.PROFILE_DETAILS_SCOPE)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.PROFILE_DETAILS_SCOPE,
            named(AuthScope.PROFILE_DETAILS_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.PROFILE_DETAILS_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        val pieChart = myPieChart as PieChart
        pieChart.setNoDataText("")
        tempInstance = savedInstanceState
        showProgressBar(true)
        random = Random()
        handler = Handler()
        val refreshLayout: SwipeRefreshLayout = profileRefresh
        refreshLayout.setOnRefreshListener {
            runnable = Runnable {
                bindView()
                showProgressBar(false)
            }
            handler.postDelayed(
                runnable, 1000
            )
        }

        bindView()
        onClick()
    }

    private fun bindView() {
        presenter.getUserInfo()
    }

    override fun bindUserInfo(user: User) {
        this.user = user
        commonBind(user.scores, user.image, user.first_name + " " + user.last_name, user.login)
    }


    private fun onClick() {

        backToMain.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        imageView10.setSafeOnClickListener {
            //fragment.setCallback(ProfileFragment.newInstance())
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.Container,
                ProfileEditFragment.newInstance(user!!),
                ProfileEditFragment.TAG
            )

        }

        settingsButton.setSafeOnClickListener {
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.Container,
                SettingsFragment.newInstance(),
                SettingsFragment.TAG
            )
        }

        helpButton.setSafeOnClickListener {
            replaceFragments(HelpFragment())
        }

        searchFriends.setSafeOnClickListener {
            activity?.supportFragmentManager?.addFragmentWithBackStack(
                R.id.Container,
                FriendsListFragment.newInstance { presenter.getUserInfo() },
                FriendsListFragment.TAG
            )
        }
    }

    override fun bindFriendsRecycler(friendsList: List<DataX>) {
        profileRefresh.isRefreshing = false
        val recyclerView: RecyclerView? = myFriendsRecycler
        if (friendsList.isNotEmpty()) {
            friendsEmptyText.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            recyclerView?.layoutManager = LinearLayoutManager(context)
            recyclerView?.adapter = FriendsListAdapter(
                { presenter.getUserInfo() },
                { closeKeyboard(requireActivity())},
                friendsList,
                context!!,
                activity!!.supportFragmentManager,
                R.id.profile_details
            )
        } else {
            friendsEmptyText.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        }
        showProgressDialog(false)
        showProgressBar(false)
        removeProgress()
    }

    override fun bindStats(stats: Stats, categoryStats: List<CategoryStat>) {
//        bindChart(stats)
        val categoriesRecycler = categoriesProfileRecycler as RecyclerView
        categoriesRecycler.layoutManager = LinearLayoutManager(context)
        categoriesRecycler.adapter =
            CategoriesProfileAdapter(categoryStats, context!!)

        presenter.myFriends()
    }

  /*  private fun bindChart(stats: Stats) {
        val pieChart = myPieChart as PieChart
        pieChart.setNoDataText("")
        if (stats.finished_games_count == 0) {
            gamesHintText.visibility = View.GONE
            gamesCount.visibility = View.GONE
            pieChart.visibility = View.GONE
            textView36.visibility = View.GONE
            categroiesScroll.visibility = View.GONE
            gamesEmptyText.visibility = View.VISIBLE
        } else {
            gamesHintText.visibility = View.VISIBLE
            gamesCount.visibility = View.VISIBLE
            pieChart.visibility = View.VISIBLE
            textView36.visibility = View.VISIBLE
            categroiesScroll.visibility = View.VISIBLE
            gamesEmptyText.visibility = View.GONE
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

    }*/

    private fun commonBind(scores: Int, image: String?, name: String, login: String) {
        if (image != null) {
            Glide.with(context!!)
                .load(image)
                .circleCrop()
                .into(profileDetailsImage)
        }
        profileDetailsImage.visibility = View.VISIBLE
        profileDetailsUserScore.setText(scores.toString() + " баллов")
        profileDetailsName.setText(name)
        profileDetailsLogin.setText("@" + login)

        presenter.getUserStats()
    }

    private fun replaceFragments(fragment: Fragment) {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.profile_details, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

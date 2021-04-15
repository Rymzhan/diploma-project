package com.thousand.bosch.views.main.presentation.profile.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.global.socket.user.SocketUser
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.auth.login.User
import com.thousand.bosch.model.main.profile.turns.Data
import com.thousand.bosch.model.main.profile.turns.Finished
import com.thousand.bosch.model.main.profile.turns.MyTurn
import com.thousand.bosch.model.main.profile.turns.Waiting
import com.thousand.bosch.model.socket.SocketMainResponseGameList
import com.thousand.bosch.model.socket.SocketMainResponseUser
import com.thousand.bosch.model.ws.Event
import com.thousand.bosch.views.adapters.FinishedListAdapter
import com.thousand.bosch.views.adapters.MyTurnAdapter
import com.thousand.bosch.views.adapters.WaitingListAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.activity.MainActivity
import com.thousand.bosch.views.main.presentation.game.results.main.ResultsMainFragment
import com.thousand.bosch.views.main.presentation.game.start.NewGameFragment
import com.thousand.bosch.views.main.presentation.profile.details.ProfileDetailsFragment
import com.thousand.bosch.views.main.presentation.profile.edit.ProfileEditFragment
import com.thousand.bosch.views.main.presentation.profile.settings.SettingsFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.*
import okio.ByteString
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

class ProfileFragment() : BaseFragment(), ProfileView, ProfileEditFragment.Callback {

    override val layoutRes: Int = R.layout.fragment_profile

    private lateinit var random: Random
    private lateinit var handler1: Handler
    private lateinit var runnable: Runnable

    private lateinit var runnable2: Runnable
    private lateinit var handler2: Handler

    private var checkBool = false

    private var handler: Handler? = null

    companion object {
        val TAG = "ProfileFragment"
        private const val NORMAL_CLOSURE_STATUS = 1000
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        getKoin().getScopeOrNull(AuthScope.PROFILE_SCOPE)?.close()
        return getKoin().getOrCreateScope(
            AuthScope.PROFILE_SCOPE,
            named(AuthScope.PROFILE_SCOPE)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.PROFILE_SCOPE)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        removeProgress()
        start1()
        clearFragments()
        onclick()
        refreshLayout()
        showProgressDialog(true)
        presenter.onFirstInit()
    }

    override fun onResume() {
        super.onResume()
        start1()
    }

    private fun start1() {
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder()
            .url("ws://185.125.91.22:8080?token=${LocalStorage.getAccessToken()}") // 'wss' - для защищенного канала
            .build()
        val wsListener = EchoWebSocketListener()
        val webSocket =
            client.newWebSocket(request, wsListener) // this provide to make 'Open ws connection'

    }

    private fun refreshLayout() {
        random = Random()
        handler1 = Handler()
        val refreshLayout: SwipeRefreshLayout = profileRefreshClick
        refreshLayout.setOnRefreshListener {
            runnable = Runnable {
                apply {
                    showProgressDialog(true)
                    bindView()
                }
            }
            handler1.postDelayed(
                runnable, 1000
            )
        }
    }

    private fun clearFragments() {
        try {
            val fm = requireActivity().supportFragmentManager
            for (i in 0 until fm.backStackEntryCount) {
                fm.popBackStack()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun bindView() {
        presenter.getUserInfo()
        presenter.getGameList()
    }

    private fun onclick() {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val gameButton = newGameButton as Button
        val scrollObj = profileScroll as NestedScrollView

        scrollObj.viewTreeObserver?.addOnScrollChangedListener {
            if (height / 5 > scrollObj.scrollY) {
                gameButton.visibility = View.VISIBLE
            } else if (height / 5 < scrollObj.scrollY) {
                gameButton.visibility = View.INVISIBLE
            }
        }

        statsLayout.setSafeOnClickListener {
            requireActivity().supportFragmentManager.addFragmentWithBackStack(
                R.id.profile,
                ResultsMainFragment.newInstance(),
                ResultsMainFragment.TAG
            )
        }

        newGameButton.setSafeOnClickListener {
            showProgress(requireContext())
            requireActivity().supportFragmentManager?.addFragmentWithBackStack(
                R.id.profile,
                NewGameFragment.newInstance(),
                NewGameFragment.TAG
            )
        }

        headerConstraint.setSafeOnClickListener {
            showProgress(requireContext())
            requireActivity().supportFragmentManager.addFragmentWithBackStack(
                R.id.Container,
                ProfileDetailsFragment.newInstance(),
                ProfileDetailsFragment.TAG
            )
        }

        settingsButton.setSafeOnClickListener {
            showProgress(requireContext())
            requireActivity().supportFragmentManager?.addFragmentWithBackStack(
                R.id.profile,
                SettingsFragment.newInstance(),
                SettingsFragment.TAG
            )
        }

        ratingImageButton.setSafeOnClickListener {
            requireActivity().supportFragmentManager?.addFragmentWithBackStack(
                R.id.profile,
                ResultsMainFragment.newInstance(),
                ResultsMainFragment.TAG
            )
        }
    }

    override fun bindUserInfo(user: User) {
        commonBind(user.scores, user.image, user.login, user.login)
    }

    override fun logOut() {
        removeProgress()
        showProgressDialog(false)
        LocalStorage.deleteStorageDATA()
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        startActivity(intent)
    }

    override fun bindTurnList(
        finished: List<Finished>?,
        myTurn: List<MyTurn>?,
        waiting: List<Waiting>?
    ) {
        apply {
            profileRefreshClick.isRefreshing = false

            textView14.visibility = View.VISIBLE
            textView18.visibility = View.VISIBLE
            textView19.visibility = View.VISIBLE

            if (finished.isNullOrEmpty()) {
                textView19.visibility = View.GONE
            }
            if (myTurn.isNullOrEmpty()) {
                textView14.visibility = View.GONE
            }
            if (waiting.isNullOrEmpty()) {
                textView18.visibility = View.GONE
            }
            ifNoGames(finished, myTurn, waiting)

            Log.i("bindTurnList 1 ", "" + myTurn?.size)
            Log.i("bindTurnList 2 ", "" + waiting?.size)
            Log.i("bindTurnList 3 ", "" + finished?.size)

            profileQueueRecycler.adapter = null
            profileWaitingRecycler.adapter = null
            profileFinishedRecycler.adapter = null


            val adapter1 =
                MyTurnAdapter(requireContext(), requireActivity(), R.id.profile, myTurn)
            profileQueueRecycler.adapter = adapter1
            profileQueueRecycler.layoutManager = LinearLayoutManager(requireContext())
            profileQueueRecycler.hasFixedSize()

            val adapter2 =
                WaitingListAdapter(requireContext(), requireActivity(), R.id.profile, waiting)
            profileWaitingRecycler.adapter = adapter2
            profileWaitingRecycler.layoutManager = LinearLayoutManager(requireContext())
            profileWaitingRecycler.hasFixedSize()

            val adapter3 =
                FinishedListAdapter(requireContext(), requireActivity(), R.id.profile, finished)
            profileFinishedRecycler.adapter = adapter3
            profileFinishedRecycler.layoutManager = LinearLayoutManager(requireContext())
            profileFinishedRecycler.hasFixedSize()

            handler = Handler()
            handler!!.postDelayed({
                showProgressDialog(false)
            }, 0)
        }
    }

    private fun ifNoGames(
        finished: List<Finished>?,
        myTurn: List<MyTurn>?,
        waiting: List<Waiting>?
    ) {
        if (finished.isNullOrEmpty() && myTurn.isNullOrEmpty() && waiting.isNullOrEmpty()) {
            emptyText.visibility = View.VISIBLE
        } else {
            emptyText.visibility = View.GONE
        }
    }

    private fun commonBind(scores: Int, image: String?, name: String, login: String) {
        profileUserScore.setText(scores.toString() + " баллов")
        profileUserNameText.setText(name)
        profileUserLoginText.setText("@" + login)


        if (image != null) {
            Glide.with(requireContext())
                .load(image)
                .circleCrop()
                .into(profileAvatar)
        }
        profileAvatar.visibility = View.VISIBLE

    }

    inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {

        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            val jsonObject = Event.getSimpleJsonObject(text!!)
            if (text.contains("user.updated")) {
                Timber.d("Receiving : %s", text)
                val message1 = Gson().fromJson(jsonObject, SocketMainResponseUser::class.java)
                output1(message1.data)
            }
            if (text.contains("game.list.updated")) {
                val message2 = Gson().fromJson(jsonObject, SocketMainResponseGameList::class.java)
                output2(message2.data)
            }

        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
            Timber.d("Receiving bytes : %s", bytes!!.hex())
        }

        override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
            webSocket!!.close(NORMAL_CLOSURE_STATUS, null)
            Timber.d("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Timber.d("Error : %s", t.message)
            start1()
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Timber.d("Closing : $code / $reason")
            start1()
        }
    }

    private fun output1(s: SocketUser) {
        activity?.runOnUiThread {
            try {
                Log.d("Outpu1 ", s.toString())

                profileUserScore.setText(s.scores.toString() + " баллов")
                profileUserNameText.setText(s.fullname)
                profileUserLoginText.setText("@" + s.login)


                if (s.image != null) {
                    Glide.with(requireContext())
                        .load(s.image)
                        .circleCrop()
                        .into(profileAvatar)
                }
                profileAvatar.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun output2(d: Data?) {
        activity?.runOnUiThread {
            try {
                if (d != null) {
                    bindTurnList2(d.finished, d.myTurn, d.waiting)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun bindTurnList2(
        finished: List<Finished>?,
        myTurn: List<MyTurn>?,
        waiting: List<Waiting>?
    ) {
        try {

            Timber.d("MyTurnSocket : ${myTurn.toString()}")
            Timber.d("WaitingSocket : ${waiting.toString()}")

            profileRefreshClick.isRefreshing = false

            textView14.visibility = View.VISIBLE
            textView18.visibility = View.VISIBLE
            textView19.visibility = View.VISIBLE

            if (finished.isNullOrEmpty()) {
                textView19.visibility = View.GONE
            }
            if (myTurn.isNullOrEmpty()) {
                textView14.visibility = View.GONE
            }
            if (waiting.isNullOrEmpty()) {
                textView18.visibility = View.GONE
            }
            ifNoGames(finished, myTurn, waiting)

            Log.i("bindTurnList 1 ", "" + myTurn?.size)
            Log.i("bindTurnList 2 ", "" + waiting?.size)
            Log.i("bindTurnList 3 ", "" + finished?.size)

            profileQueueRecycler.adapter = null
            profileWaitingRecycler.adapter = null
            profileFinishedRecycler.adapter = null


            val adapter1 =
                MyTurnAdapter(requireContext(), requireActivity(), R.id.profile, myTurn)
            profileQueueRecycler.adapter = adapter1
            profileQueueRecycler.layoutManager = LinearLayoutManager(requireContext())
            profileQueueRecycler.hasFixedSize()

            val adapter2 =
                WaitingListAdapter(requireContext(), requireActivity(), R.id.profile, waiting)
            profileWaitingRecycler.adapter = adapter2
            profileWaitingRecycler.layoutManager = LinearLayoutManager(requireContext())
            profileWaitingRecycler.hasFixedSize()

            val adapter3 =
                FinishedListAdapter(requireContext(), requireActivity(), R.id.profile, finished)
            profileFinishedRecycler.adapter = adapter3
            profileFinishedRecycler.layoutManager = LinearLayoutManager(requireContext())
            profileFinishedRecycler.hasFixedSize()

            handler = Handler()
            handler!!.postDelayed({
                showProgressDialog(false)
            }, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onChanged() {
        Timber.tag("i work").i("but for nothing")
    }

}

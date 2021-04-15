package com.thousand.bosch.views.main.presentation.game.round.friend

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.removeFragment
import com.thousand.bosch.global.extension.replaceFragmentWithBackStack
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.main.game.start.main.MainGameResponse
import com.thousand.bosch.views.adapters.FriendsResultsAdapter
import com.thousand.bosch.views.auth.di.AuthScope
import com.thousand.bosch.views.main.presentation.game.category.first.CategorySelectFragment
import com.thousand.bosch.views.main.presentation.game.category.second.CategoryConfirmFragment
import com.thousand.bosch.views.main.presentation.game.round.start.StartRoundFragment
import com.thousand.bosch.views.main.presentation.profile.main.ProfileFragment
import com.thousand.bosch.global.base.BaseFragment
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.global.extension.replaceFragment
import com.thousand.bosch.model.socket.SocketMainResponseGame
import com.thousand.bosch.model.ws.Event
import com.thousand.bosch.views.main.presentation.profile.friends.details.FriendsDetailsFragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_results_friend.*
import kotlinx.android.synthetic.main.fragment_results_friend.backToMain
import okhttp3.*
import okio.ByteString
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class ResultsFriendFragment() : BaseFragment(),
    ResultsFriendView {
    override val layoutRes: Int = R.layout.fragment_results_friend
    var checkBool: Boolean = false
    var gameId: Int? = 0
    var roundId: Int? = 0
    var currentRound: Int = 0
    var guestTitle: String? = null
    var guestImage: String? = null
    private lateinit var random: Random
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var catId = 0
    private var catName: String? = null
    private var firstQuestion: com.thousand.bosch.model.main.game.round.start.Question? = null
    private var secondQuestion: com.thousand.bosch.model.main.game.round.start.Question? = null
    private var thirdQuestion: com.thousand.bosch.model.main.game.round.start.Question? = null
    private var cb: OnBackPressedCallback? = null

    companion object {
        val TAG = "ResultsFriendFragment"
        private const val NORMAL_CLOSURE_STATUS = 1000
        const val friendId = "friendId"
        const val game = "gameId"
        fun newInstance(id: Int?, gameId1: Int?): ResultsFriendFragment {
            val fragment =
                ResultsFriendFragment()
            val args = Bundle()
            args.putInt(friendId, id!!)
            args.putInt(game, gameId1!!)
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: ResultsFriendPresenter

    @ProvidePresenter
    fun providePresenter(): ResultsFriendPresenter {
        return getKoin().getOrCreateScope(
            AuthScope.RESULTS_FRIEND,
            named(AuthScope.RESULTS_FRIEND)
        ).get()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(AuthScope.RESULTS_FRIEND)?.close()
        super.onDestroy()
    }

    override fun setUp(savedInstanceState: Bundle?) {
        removeProgress()
        showProgress(context!!)
        clearBackStack()
        refresh()
        try {
            if (arguments?.getInt(game) == 0 && arguments?.getInt(friendId) != 0) {
                presenter.startNewGame(arguments?.getInt(friendId)!!)
            } else {
                //GET GAME BY ID
                gameId = arguments?.getInt(game)!!
                presenter.gameById(gameId!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        onClick()
        start1()
        addFragment()
    }

    private fun addFragment() {
        val fragmentsCount = requireActivity().supportFragmentManager.backStackEntryCount
        if (fragmentsCount == 0) {
            setCallBack()
            apply { activity!!.onBackPressedDispatcher.addCallback(cb!!) }
        }
    }

    private fun setCallBack() {
        cb = (object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    cb!!.isEnabled = false
                    requireActivity().supportFragmentManager.replaceFragment(
                        R.id.Container,
                        ProfileFragment.newInstance(),
                        ProfileFragment.TAG
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun onClick() {
        startMainGameButton.setSafeOnClickListener {
            if (checkBool) {
                activity?.supportFragmentManager?.replaceFragmentWithBackStack(
                    R.id.results_friends_page,
                    CategorySelectFragment.newInstance(
                        gameId,
                        guestTitle,
                        guestImage,
                        arguments?.getInt(friendId)!!,
                        currentRound
                    ),
                    CategorySelectFragment.TAG
                )
            } else {
                activity?.supportFragmentManager?.replaceFragmentWithBackStack(
                    R.id.results_friends_page,
                    StartRoundFragment.newInstance(
                        roundId,
                        firstQuestion!!,
                        secondQuestion!!,
                        thirdQuestion!!,
                        catName,
                        currentRound
                    ),
                    StartRoundFragment.TAG
                )
            }
        }
        backToMain.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
        backButton.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }

        loseButton.setSafeOnClickListener {
            dialog()
        }



    }

    private fun dialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Проиграть")
        builder.setMessage("Вы уверены что хотите проиграть данную игру?")

        builder.setPositiveButton("Да") { dialog, _ ->

            showProgressDialog(true)
            presenter.loseGame(gameId!!)
            dialog.dismiss()
        }

        builder.setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alert = builder.create()
        alert.show()
    }

    private fun refresh() {
        random = Random()
        handler = Handler()
        val refreshLayout: SwipeRefreshLayout = resultFriendRefresh
        refreshLayout.setOnRefreshListener {
            runnable = Runnable {
                try {
                    if (arguments?.getInt(game) == 0 && arguments?.getInt(
                            friendId
                        ) != 0
                    ) {
                        presenter.startNewGame(arguments?.getInt(friendId)!!)
                    } else {
                        //GET GAME BY ID
                        gameId = arguments?.getInt(game)!!
                        presenter.gameById(arguments?.getInt(game)!!)
                    }
                    onClick()
                    refreshLayout.isRefreshing = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            handler.postDelayed(
                runnable, 500.toLong()
            )
        }
    }

    private fun clearBackStack() {
        try {
            activity?.supportFragmentManager?.removeFragment(CategoryConfirmFragment.TAG)
            activity?.supportFragmentManager?.removeFragment(CategorySelectFragment.TAG)
            activity?.supportFragmentManager?.removeFragment(StartRoundFragment.TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindRecyclerView(obj: MainGameResponse) {
        try {
            val tempSize = obj.data.rounds!!.size
            if (tempSize > 0){
                if (tempSize > 0 && obj.data.should_create_round) {
                    currentRound = tempSize + 1
                }
                if (tempSize > 0 && !obj.data.should_create_round) {
                    currentRound = tempSize
                }
                if (tempSize == 0) {
                    currentRound = 1
                }
                catId = obj.data.rounds[tempSize - 1].category.id
                roundId = obj.data.rounds[tempSize - 1].id
                catName = obj.data.rounds[tempSize - 1].category.title
                firstQuestion = obj.data.rounds[tempSize - 1].questions[0]
                secondQuestion = obj.data.rounds[tempSize - 1].questions[1]
                thirdQuestion = obj.data.rounds[tempSize - 1].questions[2]
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var tempGuestId = -1
        var isInFriend = false
        for (i in obj.data.players.indices) {
            if(obj.data.players[i].id !=LocalStorage.getID()){
                tempGuestId = obj.data.players[i].id
            }
            if (obj.data.players[i].id != 0) {
                loseButton.visibility = View.VISIBLE
            }
            if(obj.data.players[i].id == 0){
                loseButton.visibility = View.GONE
            }
        }

        if(loseButton.visibility == View.VISIBLE){
            imgStatisticResult.setSafeOnClickListener {
                requireActivity().supportFragmentManager.addFragmentWithBackStack(
                    R.id.Container,
                    FriendsDetailsFragment.newInstance(
                        tempGuestId,
                        null,
                        null,
                        {}),
                    FriendsDetailsFragment.TAG
                )
            }

            guestAvatar.setSafeOnClickListener {
                showAddFriendDialog()
            }

            guestName.setSafeOnClickListener {
                showAddFriendDialog()
            }
        }

        checkBool = obj.data.should_create_round
        gameId = obj.data.id
        for (i in obj.data.players.indices) {
            if (obj.data.players[i].id != LocalStorage.getID()) {
                guestTitle = if (arguments?.getInt(game) == 0 && arguments?.getInt(friendId) != 0)
                    obj.data.players[i].login
                else
                    obj.data.players[i].first_name + " " + obj.data.players[i].last_name
                guestImage = obj.data.players[i].image
            }
        }
        if (obj.data.player_turn == LocalStorage.getID()) {
            backButton.visibility = View.GONE
            startMainGameButton.visibility = View.VISIBLE
        } else {
            backButton.visibility = View.VISIBLE
            startMainGameButton.visibility = View.GONE
        }
        var creatorPoints: Int? = null
        var guestPoints: Int? = null
        var userAvatar: String? = null
        var guestAvatarUrl: String? = null
        for (i in obj.data.players.indices) {
            if (LocalStorage.getID() == obj.data.players[i].id) {
                creatorPoints = obj.data.players[i].points
                userAvatar = obj.data.players[i].image
                creatorName.text =
                    obj.data.players[i].login
            } else {
                guestPoints = obj.data.players[i].points
                guestAvatarUrl = obj.data.players[i].image
                guestName.text = if (arguments?.getInt(game) == 0 && arguments?.getInt(friendId) != 0)
                    obj.data.players[i].login
                else
                    obj.data.players[i].first_name + " " + obj.data.players[i].last_name
            }
        }
        if (userAvatar != null) {
            Glide.with(context!!)
                .load(userAvatar)
                .circleCrop()
                .into(creatorAvatar)
        }
        if (guestAvatarUrl != null) {
            Glide.with(context!!)
                .load(guestAvatarUrl)
                .circleCrop()
                .into(guestAvatar)
        }
        userPoints.text = creatorPoints.toString() + " - " + guestPoints.toString()
        val resultsRecycler = friendsResultsRecycler as RecyclerView
        resultsRecycler.layoutManager = LinearLayoutManager(context)
        resultsRecycler.adapter = FriendsResultsAdapter(obj, context!!)
        removeProgress()
    }

    private fun showAddFriendDialog(){
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Добавить в друзья")
        builder.setMessage("Вы хотите добавить ${guestTitle} в друзья?")

        builder.setPositiveButton("Да") { dialog, _ ->

            showProgressDialog(true)
            presenter.add_friend(arguments?.getInt(friendId)!!)
            dialog.dismiss()
        }

        builder.setNegativeButton("Нет", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alert = builder.create()
        alert.show()
    }

    override fun openProfileFrag() {
        try {
            activity?.supportFragmentManager?.replaceFragment(
                R.id.Container,
                ProfileFragment.newInstance(),
                ProfileFragment.TAG
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showError(message: String) {
        showMessage(message, requireView())
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

    inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {

        }

        override fun onMessage(webSocket: WebSocket?, text: String?) {
            val jsonObject = Event.getSimpleJsonObject(text!!)
            if (text.contains("game.updated")) {
                Timber.d("Receiving : %s", text)
                val message1 = Gson().fromJson(jsonObject, SocketMainResponseGame::class.java)

                if (message1.data.id == gameId) {
                    output(message1.data.id)
                }
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
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Timber.d("Closing : $code / $reason")
        }
    }

    private fun output(id: Int) {
        requireActivity().runOnUiThread {
            presenter.gameById(id)
        }
    }

    override fun addFriendResult(message: String) {
        showMessage(message, imgStatisticResult)
        showProgressDialog(false)
    }
}
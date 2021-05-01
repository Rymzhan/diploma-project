package com.diploma.stats.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diploma.stats.R
import com.diploma.stats.global.extension.addFragmentWithBackStack
import com.diploma.stats.global.utils.LocalStorage
import com.diploma.stats.model.main.profile.turns.Waiting
import com.diploma.stats.views.main.presentation.game.round.friend.ResultsFriendFragment

class WaitingListAdapter(
    private val context: Context,
    private val activity: FragmentActivity,
    private val id: Int,
    private val waiting: List<Waiting>?
) : RecyclerView.Adapter<WaitingListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout = itemView.findViewById(R.id.waitingLayout) as ConstraintLayout
        val imageView = itemView.findViewById(R.id.waitingUserImage) as ImageView
        val resultText = itemView.findViewById(R.id.waitingResultText) as TextView
        val userName = itemView.findViewById(R.id.waitingUserName) as TextView
        val leftTime = itemView.findViewById(R.id.waitingLeftTime) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.waiting_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return waiting!!.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("pushList 2 2", "" + waiting!!.size)
        val tempObj = waiting[position]
        var guestScore: Int? = null
        var userScore: Int? = null
        var guestId: Int? = null
        val gameID: Int = tempObj.id
        holder.leftTime.text = tempObj.left_time
        for (i in tempObj.players.indices) {
            if (LocalStorage.getID() != tempObj.players[i].id) {
                if (tempObj.players[i].image != null) {
                    Glide.with(context)
                        .load(tempObj.players[i].image)
                        .circleCrop()
                        .into(holder.imageView)
                }
                holder.userName.text = if (tempObj.players[i].first_name.toLowerCase() == context.getString(R.string.guest).toLowerCase())
                        tempObj.players[i].first_name + " " + tempObj.players[i].last_name
                    else
                        tempObj.players[i].login
                guestScore = tempObj.players[i].points
                guestId = tempObj.players[i].id
            } else {
                userScore = tempObj.players[i].points
            }
        }
        if (userScore != null && guestScore != null) {
            if (userScore > guestScore) {
                holder.resultText.text =
                    "Вы выигрываете со счётом " + userScore.toString() + ":" + guestScore.toString()
            }
            if (userScore < guestScore) {
                holder.resultText.text =
                    "Вы проигрываете со счётом " + userScore.toString() + ":" + guestScore.toString()
            }
            if (userScore == guestScore) {
                holder.resultText.text =
                    "Ничья со счётом " + userScore.toString() + ":" + guestScore.toString()
            }
        }
        if (guestId != 0) {
            holder.itemLayout.setOnClickListener {
                apply {
                    activity.supportFragmentManager.addFragmentWithBackStack(
                        R.id.Container,
                        ResultsFriendFragment.newInstance(guestId, 0),
                        ResultsFriendFragment.TAG
                    )
                }
            }
        } else {
            holder.itemLayout.setOnClickListener {
                apply {
                    activity.supportFragmentManager.addFragmentWithBackStack(
                        R.id.Container,
                        ResultsFriendFragment.newInstance(0, gameID),
                        ResultsFriendFragment.TAG
                    )
                }
            }
        }
    }
}
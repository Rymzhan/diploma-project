package com.thousand.bosch.views.adapters

import android.content.Context
import android.graphics.Color.*
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
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.main.profile.turns.Finished
import com.thousand.bosch.views.main.presentation.game.results.friend.GameFriendResultFragment

class FinishedListAdapter(
    private val context: Context,
    private val activity: FragmentActivity,
    private val id: Int,
    private val finished: List<Finished>?
) : RecyclerView.Adapter<FinishedListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout = itemView.findViewById(R.id.finishedLayout) as ConstraintLayout
        val imageView = itemView.findViewById(R.id.finishedUserImage) as ImageView
        val winText = itemView.findViewById(R.id.winText) as TextView
        val lostText = itemView.findViewById(R.id.lostText) as TextView
        val userName = itemView.findViewById(R.id.finishedUserName) as TextView
        val winOrLostImage = itemView.findViewById(R.id.winOrLostImage) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.finished_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return finished!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("pushList 3 3", "" + finished!!.size)
        val tempObj = finished[position]
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
            }
        }
        if (tempObj.winner_id == LocalStorage.getID()) {
            holder.winOrLostImage.setImageResource(R.drawable.ic_win_icon)
            holder.winText.visibility = View.VISIBLE
            holder.lostText.visibility = View.GONE
        }
        if (tempObj.winner_id == null) {
            holder.winOrLostImage.setImageResource(R.drawable.ic_draw_icon)
            holder.winText.visibility = View.VISIBLE
            holder.lostText.visibility = View.GONE
            holder.winText.text = "Вы сыграли вничью"
            holder.winText.setTextColor(GRAY)
        } else if (tempObj.winner_id != LocalStorage.getID()) {
            holder.winOrLostImage.setImageResource(R.drawable.ic_lost_icon)
            holder.winText.visibility = View.GONE
            holder.lostText.visibility = View.VISIBLE
        }
        var guestId = 0
        var guestName = ""
        var guestPoints = 0
        var userPoints = 0
        var guestImage: String? = null
        for (i in tempObj.players.indices) {
            if (tempObj.players[i].id != LocalStorage.getID()) {
                guestId = tempObj.players[i].id
                guestName = tempObj.players[i].first_name + " " + tempObj.players[i].last_name
                guestImage = tempObj.players[i].image
                guestPoints = tempObj.players[i].points
            } else {
                userPoints = tempObj.players[i].points
            }
        }
        holder.itemLayout.setOnClickListener {
            activity.supportFragmentManager.addFragmentWithBackStack(
                id,
                GameFriendResultFragment.newInstance(
                    guestId,
                    tempObj.winner_id,
                    guestName,
                    guestImage,
                    userPoints,
                    guestPoints
                ),
                GameFriendResultFragment.TAG
            )
        }
    }
}
package com.thousand.bosch.views.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bosch.R
import com.thousand.bosch.global.utils.LocalStorage
import com.thousand.bosch.model.main.game.start.main.MainGameResponse
import com.thousand.bosch.model.main.game.start.main.UserAnswer

class FriendsResultsAdapter(
    private val gameResponse: MainGameResponse,
    private val context: Context
) :
    RecyclerView.Adapter<FriendsResultsAdapter.ViewHolder>() {

    private var boolCheck = true
    private var ifRoundEnded = false
    private var ifGameEnded = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roundNumber: TextView = itemView.findViewById(R.id.roundNumber)
        val img0: ImageView = itemView.findViewById(R.id.question1)
        val img1: ImageView = itemView.findViewById(R.id.question2)
        val img2: ImageView = itemView.findViewById(R.id.question3)
        val img3: ImageView = itemView.findViewById(R.id.question4)
        val img4: ImageView = itemView.findViewById(R.id.question5)
        val img5: ImageView = itemView.findViewById(R.id.question6)
        val itemTitle: TextView = itemView.findViewById(R.id.categoryTitleItem)
        val icon: ImageView = itemView.findViewById(R.id.thunderIcon)
        val yourTurn: Button = itemView.findViewById(R.id.button3)
        val guestTurn: Button = itemView.findViewById(R.id.button4)
        val userImageList: List<ImageView> = listOf(img0, img1, img2)
        val guestImageList: List<ImageView> = listOf(img3, img4, img5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_results_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (ifRoundEnded) {
            Log.d("boolCheck", ifRoundEnded.toString())
            holder.icon.setImageResource(R.drawable.ic_blue_thunder)
            holder.roundNumber.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
            holder.itemTitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
            if (gameResponse.data.player_turn == LocalStorage.getID()) {
                for (i in 0 until 3) {
                    holder.userImageList[i].visibility = View.GONE
                    holder.guestImageList[i].visibility = View.VISIBLE
                }
                holder.guestTurn.visibility = View.GONE
                holder.yourTurn.visibility = View.VISIBLE
            } else {
                for (i in 0 until 3) {
                    holder.userImageList[i].visibility = View.VISIBLE
                    holder.guestImageList[i].visibility = View.GONE
                }
                holder.yourTurn.visibility = View.GONE
                holder.guestTurn.visibility = View.VISIBLE
            }
            ifRoundEnded = false
        }
        holder.itemTitle.text = "РАУНД " + (position + 1).toString()
        holder.roundNumber.text = (position + 1).toString()
        if (gameResponse.data.rounds!=null) {
            for (i in gameResponse.data.rounds.indices) {
                if (i == position) {
                    holder.itemTitle.text = gameResponse.data.rounds[i].category.title
                    break
                }
            }
        }

        if (position <= (gameResponse.data.rounds?.size!! - 1)) {
            val userResults: List<UserAnswer>? = gameResponse.data.rounds[position].user_answers
            bindRecycler(userResults, holder, position)
        }
        if (gameResponse.data.rounds.isNullOrEmpty()) {
            if (boolCheck) {
                bindEmptyRound(holder)
                boolCheck = false
            }
        }
    }

    private fun bindEmptyRound(holder: ViewHolder) {
        holder.icon.setImageResource(R.drawable.ic_blue_thunder)
        holder.roundNumber.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        holder.itemTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
        if (gameResponse.data.player_turn == LocalStorage.getID()) {
            for (i in 0 until 3) {
                holder.userImageList[i].visibility = View.GONE
            }
            holder.yourTurn.visibility = View.VISIBLE
        } else {
            for (i in 0 until 3) {
                holder.guestImageList[i].visibility = View.GONE
            }
            holder.guestTurn.visibility = View.VISIBLE
        }
    }

    private fun bindRecycler(userResults: List<UserAnswer>?, holder: ViewHolder, pos: Int) {
        if (!userResults.isNullOrEmpty()) {
            val userList: MutableList<UserAnswer> = mutableListOf()
            val guestList: MutableList<UserAnswer> = mutableListOf()
            for (i in userResults.indices) {
                if (userResults[i].user_id == LocalStorage.getID()) {
                    userList.add(userResults[i])
                } else {
                    guestList.add(userResults[i])
                }
            }

            if (guestList.isNullOrEmpty()) {
                holder.icon.setImageResource(R.drawable.ic_blue_thunder)
                holder.roundNumber.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryDark
                    )
                )
                holder.itemTitle.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimaryDark
                    )
                )
                for (i in 0 until 3) {
                    holder.guestImageList[i].visibility = View.GONE
                }
                holder.guestTurn.visibility = View.VISIBLE
            }

            for (i in userList.indices) {
                if (userList[i].is_correct == 1) {
                    holder.userImageList[i].setImageResource(R.drawable.ic_green_circle)
                }
                if (userList[i].is_correct == 0) {
                    holder.userImageList[i].setImageResource(R.drawable.ic_red_circle)
                }
            }

            for (i in guestList.indices) {
                if (guestList[i].is_correct == 1) {
                    holder.guestImageList[i].setImageResource(R.drawable.ic_green_circle)
                }
                if (guestList[i].is_correct == 0) {
                    holder.guestImageList[i].setImageResource(R.drawable.ic_red_circle)
                }
            }



            if (pos == (gameResponse.data.rounds!!.size - 1)) {
                if (gameResponse.data.rounds[pos].player_turn == null) {
                    Log.d("boolChec2k", gameResponse.data.rounds[pos].player_turn.toString())
                    if (pos == 5) {
                        ifGameEnded = true
                    } else {
                        ifRoundEnded = true
                    }
                } else {
                    holder.icon.setImageResource(R.drawable.ic_blue_thunder)
                    holder.roundNumber.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPrimaryDark
                        )
                    )
                    holder.itemTitle.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorPrimaryDark
                        )
                    )
                    if (gameResponse.data.player_turn == LocalStorage.getID()) {
                        for (i in 0 until 3) {
                            holder.userImageList[i].visibility = View.GONE
                        }
                        holder.yourTurn.visibility = View.VISIBLE
                    } else {
                        for (i in 0 until 3) {
                            holder.guestImageList[i].visibility = View.GONE
                        }
                        holder.guestTurn.visibility = View.VISIBLE
                    }
                }
            }
        } else {
            holder.icon.setImageResource(R.drawable.ic_blue_thunder)
            holder.roundNumber.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
            holder.itemTitle.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            if (gameResponse.data.player_turn == LocalStorage.getID()) {
                for (i in 0 until 3) {
                    holder.userImageList[i].visibility = View.GONE
                }
                holder.yourTurn.visibility = View.VISIBLE
            } else {
                for (i in 0 until 3) {
                    holder.guestImageList[i].visibility = View.GONE
                }
                holder.guestTurn.visibility = View.VISIBLE
            }
        }
    }

}

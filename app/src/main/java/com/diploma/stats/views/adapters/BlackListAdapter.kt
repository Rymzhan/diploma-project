package com.diploma.stats.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diploma.stats.R
import com.diploma.stats.model.main.friends.DataX

class BlackListAdapter(
    private val OnBottomReachedListener: (() -> Unit),
    val userList: List<DataX>,
    private val context: Context,
    val onCheckListener: OnItemCheckListener,
    val typeCheck: Int
) :
    RecyclerView.Adapter<BlackListAdapter.ViewHolder>() {

    interface OnItemCheckListener {
        fun onItemCheck(toUnblock: List<Int>)
        fun onItemUncheck(toUnblock: List<Int>)
    }

    private val onItemClick = onCheckListener


    var toBlock: MutableList<Int> = mutableListOf<Int>()
    var toUnblock: MutableList<Int> = mutableListOf<Int>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.blockUserName)
        val avatar: ImageView = itemView.findViewById(R.id.blockUserImage)
        val isBlocked: CheckBox = itemView.findViewById(R.id.isBLockedCheckBox)
        val card: CardView = itemView.findViewById(R.id.friendItemCard)

        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener(onClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.black_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //if(position==AppConstants.PAGE_LIMIT-1) OnBottomReachedListener.invoke()
        if (typeCheck==1){
            holder.isBlocked.visibility=View.GONE
        }
        val currentUser = userList.get(position)
        holder.userName.text = if (currentUser.first_name.toLowerCase() == context.getString(R.string.guest).toLowerCase())
            currentUser.first_name + " " + currentUser.last_name
        else
            currentUser.login
        Log.d("check123", currentUser.in_blacklist.toString())
        holder.isBlocked.isChecked = !currentUser.in_blacklist

        if (holder.isBlocked.isChecked) {
            toUnblock.add(currentUser.id)
        }

        holder.card.setOnClickListener{
            holder.isBlocked.isChecked = !holder.isBlocked.isChecked
            if (holder.isBlocked.isChecked) {
                toUnblock.add(currentUser.id)
                onItemClick.onItemCheck(toUnblock)
            } else {
                toUnblock.remove(currentUser.id)
                onItemClick.onItemUncheck(toUnblock)
            }
        }

        holder.isBlocked.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toUnblock.add(currentUser.id)
                onItemClick.onItemCheck(toUnblock)
            } else {
                toUnblock.remove(currentUser.id)
                onItemClick.onItemUncheck(toUnblock)
            }
        }
        if(currentUser.image!=null) {
            Glide.with(context)
                .load(currentUser.image)
                .circleCrop()
                .into(holder.avatar)
        }

    }

}
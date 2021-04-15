package com.thousand.bosch.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.addFragmentWithBackStack
import com.thousand.bosch.model.main.friends.DataX
import com.thousand.bosch.views.main.presentation.profile.friends.details.FriendsDetailsFragment

class FriendsListAdapter(
    private val loadData: (() -> Unit)?,
    private val CloseKeyboard: (() -> Unit),
    private val userList: List<DataX>,
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val resId: Int
) : RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.friendNameTextView)
        val textView2: TextView = itemView.findViewById(R.id.friendPointsTextView)
        val imageView: ImageView = itemView.findViewById(R.id.userSearchImage)
        val friend: CardView = itemView.findViewById(R.id.friendItemCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.friends_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //if (position == AppConstants.PAGE_LIMIT-1) OnBottomReachedListener.invoke()
        val currentItem = userList[position]
        holder.textView1.text = if (currentItem.first_name.toLowerCase() == context.getString(R.string.guest).toLowerCase())
            currentItem.first_name + " " + currentItem.last_name
        else
            currentItem.login
        holder.textView2.text = currentItem.scores.toString() + " баллов"
        if (currentItem.image != null) {
            Glide.with(context)
                .load(currentItem.image)
                .circleCrop()
                .into(holder.imageView)
        }

        holder.friend.setOnClickListener {
            CloseKeyboard.invoke()
            fragmentManager.addFragmentWithBackStack(
                R.id.Container,
                FriendsDetailsFragment.newInstance(
                    currentItem.id,
                    null,
                    currentItem.in_friends,
                    loadData!!
                ),
                FriendsDetailsFragment.TAG
            )
        }
    }
}
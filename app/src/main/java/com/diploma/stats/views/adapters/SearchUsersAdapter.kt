package com.diploma.stats.views.adapters

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
import com.diploma.stats.R
import com.diploma.stats.global.extension.addFragmentWithBackStack
import com.diploma.stats.model.main.search.SearchUser
import com.diploma.stats.views.main.presentation.profile.friends.details.FriendsDetailsFragment

class SearchUsersAdapter(
    private val CloseKeyboard: (() -> Unit),
    private val userList: List<SearchUser>,
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private val resId: Int
) : RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.friendNameTextView)
        val textView2: TextView = itemView.findViewById(R.id.friendPointsTextView)
        val imageView: ImageView = itemView.findViewById(R.id.userSearchImage)
        val cardView: CardView = itemView.findViewById(R.id.friendItemCard)
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
            holder.cardView.setOnClickListener {
                CloseKeyboard.invoke()
                fragmentManager.addFragmentWithBackStack(
                    resId,
                    FriendsDetailsFragment.newInstance(
                        currentItem.id,
                        currentItem,
                        currentItem.in_friends,
                        {}),
                    FriendsDetailsFragment.TAG
                )
            }
        }
    }
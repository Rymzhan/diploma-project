package com.diploma.stats.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diploma.stats.R
import com.diploma.stats.global.extension.addFragmentWithBackStack
import com.diploma.stats.global.utils.LocalStorage
import com.diploma.stats.model.list.top.Data
import com.diploma.stats.model.main.search.SearchUser
import com.diploma.stats.views.main.presentation.profile.details.ProfileDetailsFragment
import com.diploma.stats.views.main.presentation.profile.friends.details.FriendsDetailsFragment

class TopTwentyAdapter(private val context: Context, private val activity: FragmentActivity) :
    RecyclerView.Adapter<TopTwentyAdapter.ViewHolder>() {

    private var myList: List<Data>? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName = itemView.findViewById(R.id.name) as TextView
        val avatar = itemView.findViewById(R.id.avatar) as ImageView
        val rating = itemView.findViewById(R.id.rating) as TextView
        val points = itemView.findViewById(R.id.points) as TextView
        val view2 = itemView.findViewById(R.id.top_list) as ConstraintLayout
        val placeImageView = itemView.findViewById(R.id.imageView41) as ImageView
    }

    fun pushAdapter(list: List<Data>) {
        myList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.top_list_item, parent, false)
        return TopTwentyAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return myList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curUser = myList!![position]
        val searchUser = SearchUser(
            curUser.first_name,
            curUser.id,
            curUser.image,
            curUser.in_blacklist,
            curUser.in_friends,
            curUser.last_name,
            curUser.login,
            curUser.rating,
            curUser.scores
        )
        if (!curUser.image.isNullOrEmpty()) {
            Glide.with(context)
                .load(curUser.image)
                .circleCrop()
                .into(holder.avatar)
        }
        holder.userName.text = if (curUser.first_name.toLowerCase() == context.getString(R.string.guest).toLowerCase())
            curUser.first_name + " " + curUser.last_name
        else
            curUser.login
        holder.points.text = curUser.scores.toString()
        if (position == 0) {
            holder.placeImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_first_icon
                )
            )
        }
        if (position == 1) {
            holder.placeImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_second_icon
                )
            )
        }
        if (position == 2) {
            holder.placeImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_third_icon
                )
            )
        }
        if (position > 2) {
            holder.rating.text = if(curUser.position == null) (position + 1).toString() else curUser.position.toString()
            holder.placeImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.blue_gradient
                )
            )
            //android:src="@drawable/blue_gradient"
        }

        holder.view2.setOnClickListener {
            if (curUser.id == LocalStorage.getID()) {
                activity.supportFragmentManager.addFragmentWithBackStack(
                    R.id.Container,
                    ProfileDetailsFragment.newInstance(),
                    ProfileDetailsFragment.TAG
                )
            } else {
                if(curUser.in_friends){
                    activity.supportFragmentManager.addFragmentWithBackStack(
                        R.id.Container,
                        FriendsDetailsFragment.newInstance(
                            curUser.id,
                            null,
                            curUser.in_friends,
                            {}
                        ),
                        FriendsDetailsFragment.TAG
                    )
                }else{
                    activity.supportFragmentManager.addFragmentWithBackStack(
                        R.id.Container,
                        FriendsDetailsFragment.newInstance(
                            curUser.id,
                            searchUser,
                            curUser.in_friends,
                            {}
                        ),
                        FriendsDetailsFragment.TAG
                    )
                }
            }
        }
    }
}
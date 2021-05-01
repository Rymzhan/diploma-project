package com.diploma.stats.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.diploma.stats.R
import com.diploma.stats.views.main.presentation.game.round.friend.ResultsFriendFragment

class ProfileAdapter(val fragmentManager: FragmentManager,val id: Int) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemLayout = itemView.findViewById(R.id.profileItemConstraint) as ConstraintLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.profile_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemLayout.setOnClickListener {
            val fragmentManager = fragmentManager
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.profile,
                ResultsFriendFragment()
            )
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}
package com.thousand.bosch.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thousand.bosch.R
import com.thousand.bosch.model.main.profile.statistics.CategoryStat

class CategoriesProfileAdapter(
    private val catList: List<CategoryStat>,
    private val context: Context
) :
    RecyclerView.Adapter<CategoriesProfileAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catImage = itemView.findViewById(R.id.categoryImage) as ImageView
        val catTitle = itemView.findViewById(R.id.categoryTitle) as TextView
        val catGamesCount = itemView.findViewById(R.id.categoryGameCount) as TextView
        val catLayout = itemView.findViewById(R.id.categoryLayout) as ConstraintLayout
        val catPercentage = itemView.findViewById(R.id.categoryPercentage) as TextView
        val catProgress = itemView.findViewById(R.id.categoryProgressBar) as ProgressBar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
            .inflate(R.layout.categories_profile_item, parent, false)
        return ViewHolder(
            v
        )

    }

    override fun getItemCount(): Int {
        return catList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempObj = catList[position]
        if (tempObj.image != null) {
            Glide.with(context)
                .load(tempObj.image)
                .circleCrop()
                .into(holder.catImage)
        }
        holder.catGamesCount.text =
            tempObj.games_count.toString() + " игр • " + tempObj.correct_answers.toString() + " из " + tempObj.answers
        holder.catTitle.text = tempObj.title
        val percent: Int = (tempObj.correct_answers * 100) / tempObj.answers
        holder.catPercentage.text = "$percent%"
        holder.catProgress.progress = percent
    }

}
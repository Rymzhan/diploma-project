package com.thousand.bosch.views.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.thousand.bosch.R
import com.thousand.bosch.global.extension.replaceFragmentWithBackStack
import com.thousand.bosch.global.extension.setImageUrl
import com.thousand.bosch.model.main.game.categories.RandomCategories
import com.thousand.bosch.model.main.game.categories.RandomCategoriesItem
import com.thousand.bosch.views.main.presentation.game.category.second.CategoryConfirmFragment

class CategoryAdapter(
    private val showProgress: (() -> Unit),
    private val gameId: Int?,
    private val friendId: Int,
    private val activity: FragmentActivity,
    private val context: Context,
    private val curRound: Int
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var mData: MutableList<RandomCategoriesItem> = mutableListOf()

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val categoryText: TextView = view.findViewById(R.id.categoryImageText)
        val categoryCard: CardView = view.findViewById(R.id.categoryCard)
        val categoryImage: ImageView = view.findViewById(R.id.categoryImageSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(v)
    }

    fun setDataList(data: RandomCategories) {
        mData = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryText.text = mData[position].title
        holder.categoryImage.setImageUrl(mData[position].image)
        holder.categoryCard.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            override fun onGlobalLayout() {
                val drawable = ContextCompat.getDrawable(context, R.drawable.bottom_tint)
                drawable!!.setBounds(0, 0, holder.categoryCard.width, holder.categoryCard.height)
                holder.categoryCard.overlay.add(drawable)
                holder.categoryCard.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        )
        holder.categoryCard.setOnClickListener {
            showProgress.invoke()
            activity.supportFragmentManager.replaceFragmentWithBackStack(
                R.id.Container,
                CategoryConfirmFragment.newInstance(
                    {},
                    gameId,
                    mData[position].id,
                    mData[position].image,
                    mData[position].title,
                    friendId,
                    curRound
                ),
                CategoryConfirmFragment.TAG
            )
        }
    }
}
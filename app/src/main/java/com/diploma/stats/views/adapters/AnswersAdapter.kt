package com.diploma.stats.views.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.diploma.stats.R
import com.diploma.stats.model.main.game.round.start.Answer


class AnswersAdapter(private val onSelectedListener: OnSelectedListener) :
    RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {
    private var checkBool = true
    private lateinit var context: Context
    private var currentPos: Int = 0
    private var mData: MutableList<Answer> = mutableListOf()
    var tempHolder: ViewHolder? = null

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val ansText: TextView = view.findViewById(R.id.answerText)
        val ansCard: CardView = view.findViewById(R.id.answerCard)
    }

    interface OnSelectedListener {
        fun onSelected(answerId: Int, isCorrect: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_answer, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setDataList(data: MutableList<Answer>) {
        mData = data
        notifyDataSetChanged()
    }

    fun setBool(bol: Boolean) {
        checkBool = bol
        notifyDataSetChanged()
    }

    fun setCurrentPos(current_pos: Int) {
        this.currentPos = current_pos
        notifyDataSetChanged()
    }

    fun addContext(context: Context) {
        this.context = context
    }

    private fun showCorrect() {
        tempHolder!!.ansCard.setCardBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.green
            )
        )
        tempHolder!!.ansText.setTextColor(Color.WHITE)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mData[position].is_correct == 1) {
            tempHolder = holder
        }
        holder.apply {
            ansText.text = mData[position].title
            ansCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            ansText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))

            ansCard.setOnClickListener {
                if (checkBool) {
                    checkBool = if (mData[position].is_correct == 1) {
                        ansCard.setCardBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.green
                            )
                        )
                        ansText.setTextColor(Color.WHITE)
                        onSelectedListener.onSelected(
                            mData[position].id,
                            mData[position].is_correct
                        )
                        false
                    } else {
                        ansCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red))
                        ansText.setTextColor(Color.WHITE)
                        onSelectedListener.onSelected(
                            mData[position].id,
                            mData[position].is_correct
                        )
                        showCorrect()
                        false
                    }
                } else {
                    return@setOnClickListener
                }
            }
        }
    }
}
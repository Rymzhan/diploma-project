package com.diploma.stats.views.main.presentation.stats.department.course

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diploma.stats.R
import com.diploma.stats.model.department.dep_list.CourseResult

class StatsByCourseAdapter(private val cityList: List<CourseResult>): RecyclerView.Adapter<StatsByCourseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        val entTextView: TextView = itemView.findViewById(R.id.entTextView)
        val countTextView: TextView = itemView.findViewById(R.id.countTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.by_city_item, parent, false)
        return StatsByCourseAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityTextView.text = cityList[position].name
        holder.entTextView.text = cityList[position].retake.toString().take(4)
        cityList[position].total?.let { holder.countTextView.text = it.toString().take(4) }
    }

}
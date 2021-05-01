package com.diploma.stats.views.main.presentation.stats.student.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diploma.stats.R
import com.diploma.stats.model.department.dep_list.StudentCityResult

class StatsByCityAdapter(private val cityList: List<StudentCityResult>): RecyclerView.Adapter<StatsByCityAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        val entTextView: TextView = itemView.findViewById(R.id.entTextView)
        val countTextView: TextView = itemView.findViewById(R.id.countTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.by_city_item, parent, false)
        return StatsByCityAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int = cityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cityTextView.text = cityList[position].name
        holder.countTextView.text = cityList[position].count.toString()
        cityList[position].ent?.let { holder.entTextView.text = it }
    }

}
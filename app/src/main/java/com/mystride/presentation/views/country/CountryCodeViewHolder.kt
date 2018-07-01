package com.mystride.presentation.views.country
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mystride.mystride.R

class CountryCodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val country_name: TextView = view.findViewById(R.id.country_name)
    val country_code: TextView = view.findViewById(R.id.country_code)

    fun bindData() {
    }
}
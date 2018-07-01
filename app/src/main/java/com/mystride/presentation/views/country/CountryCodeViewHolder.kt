package com.mystride.presentation.views.country

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mystride.data.remote.models.CountryModel
import com.mystride.mystride.R

class CountryCodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val countryName: TextView = view.findViewById(R.id.country_name)
    val countryCode: TextView = view.findViewById(R.id.country_code)

    fun bindData(countryModel: CountryModel) {
        countryName.text = countryModel.name
        countryCode.text = countryModel.dial_code
    }
}
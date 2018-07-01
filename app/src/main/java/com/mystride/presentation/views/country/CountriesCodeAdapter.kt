package com.mystride.presentation.views.country

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mystride.data.remote.models.CountryModel
import com.mystride.mystride.R

class CountriesCodeAdapter(private val countriesList: List<CountryModel>, val countriesInterface: CountriesInterface) : RecyclerView.Adapter<CountryCodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryCodeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CountryCodeViewHolder(layoutInflater.inflate(R.layout.country_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CountryCodeViewHolder, position: Int) {
        holder.bindData(countriesList[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            countriesInterface.onCountrySelected(countriesList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

}
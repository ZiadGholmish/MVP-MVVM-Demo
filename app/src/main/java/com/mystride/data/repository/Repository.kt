package com.mystride.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mystride.data.remote.models.CountryModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(val context: Context, val gson: Gson) {

    fun getCountriesList(): Single<List<CountryModel>> {
        return Single.create<List<CountryModel>> {
            val countriesToken = object : TypeToken<List<CountryModel>>() {}
            var json: String?
            try {
                val inputStream = context.assets.open("countries.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charset.forName("UTF-8"))
                it.onSuccess(Gson().fromJson(json, countriesToken.type))
            } catch (ex: IOException) {
                ex.printStackTrace()
                it.onError(ex)
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}
package com.mystride.data.remote.models

import android.os.Parcel
import android.os.Parcelable

data class CountryModel(val name: String, val dial_code: String, val code: String, val mask: String,
                        val mask_hint: String, val number_length: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(dial_code)
        parcel.writeString(code)
        parcel.writeString(mask)
        parcel.writeString(mask_hint)
        parcel.writeInt(number_length)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CountryModel> {
        override fun createFromParcel(parcel: Parcel): CountryModel {
            return CountryModel(parcel)
        }

        override fun newArray(size: Int): Array<CountryModel?> {
            return arrayOfNulls(size)
        }
    }
}
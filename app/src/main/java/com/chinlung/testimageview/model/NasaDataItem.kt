package com.chinlung.testimageview.model

import android.os.Parcel
import android.os.Parcelable

data class NasaDataItem(
    val date: String? = "",
    val title: String? = "",
    val url: String? = "",
    val description: String? =""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NasaDataItem> {
        override fun createFromParcel(parcel: Parcel): NasaDataItem {
            return NasaDataItem(parcel)
        }

        override fun newArray(size: Int): Array<NasaDataItem?> {
            return arrayOfNulls(size)
        }
    }
}
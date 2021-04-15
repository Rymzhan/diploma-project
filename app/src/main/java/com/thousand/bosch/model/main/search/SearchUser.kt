package com.thousand.bosch.model.main.search

import android.os.Parcel
import android.os.Parcelable

data class SearchUser(
    val first_name: String,
    val id: Int,
    val image: String?,
    val in_blacklist: Boolean,
    val in_friends: Boolean,
    val last_name: String,
    val login: String,
    val rating: Int,
    val scores: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeByte(if (in_blacklist) 1 else 0)
        parcel.writeByte(if (in_friends) 1 else 0)
        parcel.writeString(last_name)
        parcel.writeString(login)
        parcel.writeInt(rating)
        parcel.writeInt(scores)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchUser> {
        override fun createFromParcel(parcel: Parcel): SearchUser {
            return SearchUser(parcel)
        }

        override fun newArray(size: Int): Array<SearchUser?> {
            return arrayOfNulls(size)
        }
    }
}
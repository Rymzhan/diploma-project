package com.thousand.bosch.model.main.game.round.start

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_correct")
    val is_correct: Int,
    @SerializedName("title")
    val title: String,
    var isClickable: Boolean = true
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(is_correct)
        parcel.writeString(title)
        parcel.writeByte(if (isClickable) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Answer> {
        override fun createFromParcel(parcel: Parcel): Answer {
            return Answer(parcel)
        }

        override fun newArray(size: Int): Array<Answer?> {
            return arrayOfNulls(size)
        }
    }
}
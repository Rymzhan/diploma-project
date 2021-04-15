package com.thousand.bosch.model.main.game.start.main

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val answers: List<Answer>,
    val id: Int,
    val title: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Answer)!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(answers)
        parcel.writeInt(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}
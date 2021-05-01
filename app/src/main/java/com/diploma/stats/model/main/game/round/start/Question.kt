package com.diploma.stats.model.main.game.round.start

import android.os.Parcel
import android.os.Parcelable

data class Question(
    val answers: List<Answer>,
    val id: Int,
    val title: String,
    val image: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Answer)!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(answers)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(image)
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
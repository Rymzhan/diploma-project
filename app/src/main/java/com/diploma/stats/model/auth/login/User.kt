package com.diploma.stats.model.auth.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val city_id: Int,
    val country_id: Int,
    val first_name: String,
    val id: Int,
    val image: String?,
    val last_name: String,
    val login: String,
    val organization: String,
    val phone: String,
    val rating: Int,
    val scores: Int,
    val workplace: String,
    val position: Int?
): Parcelable

package com.diploma.stats.model.department.dep_list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Department(
    val id: Int,
    val name: String
): Parcelable{
    override fun toString(): String {
        return name
    }
}

@Parcelize
data class Group(
    val id: Int,
    val department_id: Int,
    val name: String
): Parcelable{
    override fun toString(): String {
        return name
    }
}

@Parcelize
data class Course(
    val id: Int,
    val name: String
): Parcelable{
    override fun toString(): String {
        return name
    }
}

data class City(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}

@Parcelize
data class StudentResponse(
    val kaz_history: String,
    val mathematics : String,
    val gr_ct : String,
    val prof_first_point : String,
    val prof_second_point : String,
    val rus : String,
    val kaz : String,
    val ielts_yes : String,
    val ielts_no : String,
    val ielts_point : String,
    val name: String,
    val surname: String,
    val iin: String,
    val sum_of_points: String,
    val likelihood_acquittal_ent: String
): Parcelable

@Parcelize
data class StudentCityResult(
    val count: Int,
    val name: String,
    val ent: String?
): Parcelable

@Parcelize
data class CourseResult(
    val total: Double?,
    val name: String?,
    val retake: Double?
): Parcelable
package com.thousand.bosch.model.department.dep_list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Department(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}

data class Group(
    val id: Int,
    val department_id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}

data class Course(
    val id: Int,
    val teacher: String,
    val name: String
){
    override fun toString(): String {
        return "$name $teacher"
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
    val ielts_point : String
): Parcelable
package com.diploma.stats.global.service

import com.diploma.stats.model.common.Pagination
import com.diploma.stats.model.department.dep_list.*
import com.diploma.stats.model.department.response.DepartmentResponse
import io.reactivex.Single
import retrofit2.http.*

interface ServerService {

    @GET("cities_statistic")
    fun statsByCities(
        @Query("page") page: Int,
        @Query("sort_key") sort_key: String
    ): Single<Pagination>

    @GET("course_statistic")
    fun statsByCourses(
        @Query("page") page: Int,
        @Query("sort_key") sort_key: String,
        @Query("department_id") department_id: Int?,
        @Query("course_id") course_id: Int?
    ): Single<Pagination>

    @GET(Endpoints.DEPARTMENT_LIST)
    fun departmentList(): Single<MutableList<Department>>

    @GET(Endpoints.GROUP_LIST)
    fun groupList(
        @Query("department_id") department_id: Int
    ): Single<MutableList<Group>>

    @GET("course")
    fun getCourses(
        @Query("department_id") group_id: Int
    ): Single<MutableList<Course>>

    @GET("course")
    fun getCourses2(
        @Query("group_id") department_id: Int
    ): Single<MutableList<Course>>

    @GET("analyz_one")
    fun getCalcByGroup(
        @Query("group_id") group_id: Int?,
        @Query("course_id") course_id: Int?,
        @Query("department_id") department_id: Int?
    ): Single<MutableList<DepartmentResponse>>

    @GET("cities")
    fun citiesList(): Single<MutableList<City>>

    @GET("students")
    fun getStudentResult(
        @Query("iin") iin: Long?,
        @Query("city_id") city_id: Int?,
        @Query("last_name") last_name: String?,
        @Query("first_name") first_name: String?
    ): Single<MutableList<StudentResponse>>

    @GET("statistic/correlation_of_ent_to_grades")
    fun getCorellation(): Single<String>
}
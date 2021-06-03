package com.diploma.stats.views.scope.repositories

import com.diploma.stats.model.common.PaginationMain
import com.google.gson.JsonObject
import com.diploma.stats.model.common.Pagination
import com.diploma.stats.model.department.dep_list.*
import com.diploma.stats.model.department.response.DepartmentResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface UserRepo {

    fun statsByCities(page: Int, key: String): Single<Pagination>

    fun statsByCourses(page: Int, key: String, department_id: Int?, course_id: Int?): Single<Pagination>

    fun departmentLis(): Single<MutableList<Department>>

    fun groupList(dep_id: Int): Single<MutableList<Group>>

    fun getCourses(department_id: Int): Single<MutableList<Course>>
    fun getCourses2(department_id: Int): Single<MutableList<Course>>

    fun getCalcByGroup(
        group_id: Int?,
        course_id: Int?,
        department_id: Int?
    ): Single<MutableList<DepartmentResponse>>

    fun citiesList(): Single<MutableList<City>>

    fun getStudentResult(
        iin: Long?,
         city_id: Int?,
        last_name: String?,
       first_name: String?
    ): Single<MutableList<StudentResponse>>

    fun getCorellation(): Single<String>
}
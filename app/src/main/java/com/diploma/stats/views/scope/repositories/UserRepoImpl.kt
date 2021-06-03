package com.diploma.stats.views.scope.repositories

import com.diploma.stats.global.service.ServerService
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

class UserRepoImpl(
    private val serverService: ServerService
) : UserRepo {

    override fun statsByCities(page: Int, key: String): Single<Pagination> =
        serverService.statsByCities(page,key)

    override fun statsByCourses(page: Int, key: String, department_id: Int?, course_id: Int?): Single<Pagination>  =
        serverService.statsByCourses(page,key, department_id, course_id)

    override fun departmentLis(): Single<MutableList<Department>> =
        serverService.departmentList()

    override fun groupList(dep_id: Int): Single<MutableList<Group>> =
        serverService.groupList(dep_id)

    override fun getCourses(department_id: Int): Single<MutableList<Course>> =
        serverService.getCourses(department_id)

    override fun getCourses2(department_id: Int): Single<MutableList<Course>> =
        serverService.getCourses2(department_id)

    override fun getCalcByGroup(
        group_id: Int?,
        course_id: Int?,
        department_id: Int?
    ): Single<MutableList<DepartmentResponse>> =
        serverService.getCalcByGroup(group_id, course_id, department_id)

    override fun citiesList(): Single<MutableList<City>> =
        serverService.citiesList()

    override fun getStudentResult(
        iin: Long?,
        city_id: Int?,
        last_name: String?,
        first_name: String?
    ): Single<MutableList<StudentResponse>> =
         serverService.getStudentResult(iin, city_id, last_name, first_name)

    override fun getCorellation(): Single<String> = serverService.getCorellation()
}
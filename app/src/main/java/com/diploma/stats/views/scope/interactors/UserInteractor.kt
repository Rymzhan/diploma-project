package com.diploma.stats.views.scope.interactors

import com.diploma.stats.global.system.SchedulersProvider
import com.diploma.stats.views.scope.repositories.UserRepo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.diploma.stats.model.department.dep_list.*
import com.diploma.stats.model.department.response.DepartmentResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class UserInteractor(
    private val userRepository: UserRepo,
    private val schedulersProvider: SchedulersProvider
) {

    fun statsByCities(key: String,id: Int): Single<List<StudentCityResult>> = userRepository.statsByCities(id,key)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
        .map {
            val typeToken = object : TypeToken<List<StudentCityResult>>() {}.type
            return@map Gson().fromJson<List<StudentCityResult>>(it.data, typeToken)
        }

    fun statsByCourses(key: String,id: Int, department_id: Int?, course_id: Int?): Single<List<CourseResult>> = userRepository.statsByCourses(id,key,department_id, course_id)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
        .map {
            val typeToken = object : TypeToken<List<CourseResult>>() {}.type
            return@map Gson().fromJson<List<CourseResult>>(it.data, typeToken)
        }

    fun departmentList(): Single<MutableList<Department>> =
        userRepository.departmentLis()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun groupList(dep_id: Int): Single<MutableList<Group>>  =
        userRepository.groupList(dep_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCourses(department_id: Int): Single<MutableList<Course>> =
        userRepository.getCourses(department_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())


    fun getCourses2(departmentId: Int): Single<MutableList<Course>> =
        userRepository.getCourses2(departmentId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCalcByGroup(
        group_id: Int?,
        course_id: Int?,
        department_id: Int?
    ): Single<MutableList<DepartmentResponse>> =
    userRepository.getCalcByGroup(group_id, course_id, department_id)
    .subscribeOn(schedulersProvider.io())
    .observeOn(schedulersProvider.ui())

    fun citiesList(): Single<MutableList<City>> =
        userRepository.citiesList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getStudentResult(
        iin: Long?,
        city_id: Int?,
        last_name: String?,
        first_name: String?
    ): Single<MutableList<StudentResponse>> =
        userRepository.getStudentResult(iin, city_id, last_name, first_name)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCorellation(): Single<String> = userRepository.getCorellation()
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())


}
package com.thousand.bosch.views.auth.repositories

import com.thousand.bosch.model.auth.login.MainResponse
import com.thousand.bosch.model.common.PaginationMain
import com.thousand.bosch.model.invite.InviteModel
import com.thousand.bosch.model.list.Cities
import com.thousand.bosch.model.list.CitiesItem
import com.thousand.bosch.model.list.Countries
import com.thousand.bosch.model.list.CountriesItem
import com.thousand.bosch.model.list.top.TopListResponse
import com.thousand.bosch.model.main.friends.FriendsResponse
import com.thousand.bosch.model.main.game.categories.RandomCategories
import com.thousand.bosch.model.main.game.round.start.RoundStartModel
import com.thousand.bosch.model.main.game.start.main.MainGameResponse
import com.thousand.bosch.model.main.profile.statistics.StatisticsModel
import com.thousand.bosch.model.main.profile.turns.TurnsResponse
import com.thousand.bosch.model.web_view.WebViewModel
import com.google.gson.JsonObject
import com.thousand.bosch.model.department.dep_list.*
import com.thousand.bosch.model.department.response.DepartmentResponse
import com.thousand.bosch.model.list.top.Top20ListResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Query

interface UserRepo {
    fun login(phone: String, password: String, deviceToken: String): Single<Response<MainResponse>>

    fun confirm_registr(phone: String, code: String): Single<MainResponse>

    fun suggestQuestion(email: String, id: Int, description: String): Single<Response<JsonObject>>

    fun register(phone: String): Single<Response<MainResponse>>

    fun getCountries(): Observable<Countries>

    fun getCities(id: Int): Observable<Cities>

    fun getCountryById(id: Int): Observable<CountriesItem>

    fun getCityById(country_id: Int, city_id: Int): Observable<CitiesItem>

    fun invite(): Observable<InviteModel>

    fun reset_request(phone: String): Single<MainResponse>

    fun reset_confirm(phone: String, code: String): Single<MainResponse>

    fun newGameFriend(id: Int): Single<Response<MainGameResponse>>

    fun getTop(): Observable<Top20ListResponse>

    fun getTopFriends(): Observable<TopListResponse>

    // WebSocket
    // fun getMessageSocket(): Flowable<SocketMainResponse>

    fun getTopByCountry(country_id: Int): Observable<TopListResponse>

    fun getTopByCity(country_id: Int, city_id: Int): Observable<TopListResponse>

    fun updateProfile(
        login: RequestBody?,
        first_name: RequestBody?,
        last_name: RequestBody?,
        organization: RequestBody?,
        workplace: RequestBody?,
        country_id: RequestBody?,
        city_id: RequestBody?,
        password: RequestBody?,
        image: MultipartBody.Part?
    ): Single<Response<JsonObject>>

    fun reset_new_password(
        phone: String,
        reset_token: String,
        new_password: String
    ): Single<MainResponse>

    fun search_users(text: String, token: String, id: Int): Single<PaginationMain>

    fun getWebView(): Observable<WebViewModel>

    fun userStatistics(id: Int): Observable<Response<StatisticsModel>>

    fun myStats(): Observable<Response<StatisticsModel>>

    fun user_info(): Observable<Response<JsonObject>>

    fun friend_info(id: Int): Observable<Response<JsonObject>>

    fun add_friend(id: Int): Observable<JsonObject>

    fun loseGame(id: Int): Observable<Response<JsonObject>>

    fun loseRound(id: Int): Observable<Response<JsonObject>>

    fun startRound(id: Int, categoryId: Int): Observable<Response<RoundStartModel>>

    fun gameById(id: Int): Single<Response<MainGameResponse>>

    fun randomGame(): Observable<Response<MainGameResponse>>

    fun answerToRound(
        roundId: Int,
        firstQuestion: Int,
        firstAnswer: Int,
        secondQuestion: Int,
        secondAnswer: Int,
        thirdQuestion: Int,
        thirdAnswer: Int
    ): Single<Response<JsonObject>>

    fun categoriesRandom(): Observable<Response<RandomCategories>>

    fun gamesList(): Observable<Response<TurnsResponse>>

    fun list_friends(): Observable<FriendsResponse>

    fun list_friends2(id: Int): Single<PaginationMain>

    fun blackLis2t(id: Int): Single<PaginationMain>

    fun log_out(): Single<MainResponse>

    fun blackList(): Observable<FriendsResponse>

    fun delete_user(id: Int): Observable<JsonObject>

    fun blockList(list: MutableList<Int>): Single<Response<JsonObject>>

    fun userById(id: Int): Observable<Response<JsonObject>>

    fun removeList(list: MutableList<Int>): Single<Response<JsonObject>>

    fun create_user(
        login: String,
        first_name: String,
        last_name: String,
        organization: String,
        workplace: String,
        country_id: Int,
        city_id: Int,
        phone: String, registration_token: String,
        password: String,
        image: String?,
        deviceToken: String
    ): Single<Response<MainResponse>>

    fun deleteAccount(): Completable

    fun departmentLis(): Single<MutableList<Department>>

    fun groupList(dep_id: Int): Single<MutableList<Group>>

    fun getCourses(department_id: Int): Single<MutableList<Course>>

    fun getCalcByGroup(
        group_id: Int,
        course_id: Int
    ): Single<MutableList<DepartmentResponse>>

    fun citiesList(): Single<MutableList<City>>

    fun getStudentResult(
        iin: Long?,
         city_id: Int?,
        last_name: String?,
       first_name: String?
    ): Single<MutableList<StudentResponse>>
}
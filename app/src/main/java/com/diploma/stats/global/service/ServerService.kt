package com.diploma.stats.global.service

import com.diploma.stats.model.auth.login.MainResponse
import com.diploma.stats.model.common.PaginationMain
import com.diploma.stats.model.invite.InviteModel
import com.diploma.stats.model.list.Cities
import com.diploma.stats.model.list.CitiesItem
import com.diploma.stats.model.list.Countries
import com.diploma.stats.model.list.CountriesItem
import com.diploma.stats.model.list.top.TopListResponse
import com.diploma.stats.model.main.friends.FriendsResponse
import com.diploma.stats.model.main.game.categories.RandomCategories
import com.diploma.stats.model.main.game.round.start.RoundStartModel
import com.diploma.stats.model.main.game.start.main.MainGameResponse
import com.diploma.stats.model.main.profile.statistics.StatisticsModel
import com.diploma.stats.model.main.profile.turns.TurnsResponse
import com.diploma.stats.model.web_view.WebViewModel
import com.google.gson.JsonObject
import com.diploma.stats.model.auth.login.LoginRequest
import com.diploma.stats.model.common.Pagination
import com.diploma.stats.model.department.dep_list.*
import com.diploma.stats.model.department.response.DepartmentResponse
import com.diploma.stats.model.list.top.Top20ListResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ServerService {
    @POST(Endpoints.SIGNIN)
    fun login(
        @Body loginRequest: LoginRequest
    ): Single<Response<MainResponse>>

    @FormUrlEncoded
    @POST(Endpoints.REGISTER)
    fun register(
        @Field("phone") phone: String
    ): Single<Response<MainResponse>>

    @FormUrlEncoded
    @POST(Endpoints.SUGGEST_QUESTION)
    fun suggestQuestion(
        @Field("email") email: String,
        @Field("category_id") id: Int,
        @Field("description") description: String
    ): Single<Response<JsonObject>>

    @FormUrlEncoded
    @POST(Endpoints.CONFIRM_REGISTER)
    fun confirm(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Single<MainResponse>

    @FormUrlEncoded
    @POST(Endpoints.RESET_REQUEST)
    fun reset_request(
        @Field("phone") phone: String
    ): Single<MainResponse>

    @Multipart
    @POST(Endpoints.EDIT_PROFILE)
    fun updateProfile(
        @Part("login") login: RequestBody?,
        @Part("first_name") first_name: RequestBody?,
        @Part("last_name") last_name: RequestBody?,
        @Part("organization") organization: RequestBody?,
        @Part("workplace") workplace: RequestBody?,
        @Part("country_id") country_id: RequestBody?,
        @Part("city_id") city_id: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part image: MultipartBody.Part?
    ): Single<Response<JsonObject>>

    @FormUrlEncoded
    @POST(Endpoints.NEW_GAME_FRIEND)
    fun newGameFriend(
        @Field("friend_id") id: Int
    ): Single<Response<MainGameResponse>>

    @FormUrlEncoded
    @POST(Endpoints.RESET_CONFIRM)
    fun reset_confirm(
        @Field("phone") phone: String,
        @Field("code") code: String
    ): Single<MainResponse>

    @POST(Endpoints.BLOCK_LIST)
    fun blockUserList(
        @Header("Content-type") key: String = "application/json",
        @Body list: MutableList<Int>
    ): Single<Response<JsonObject>>

    @HTTP(method = "DELETE", path = Endpoints.REMOVE_LIST, hasBody = true)
    fun removeList(
        @Header("Content-type") key: String = "application/json",
        @Body list: MutableList<Int>
    ): Single<Response<JsonObject>>

    @GET(Endpoints.USER_BY_ID)
    fun userById(
        @Path("id") id: Int
    ): Observable<Response<JsonObject>>

    @FormUrlEncoded
    @POST(Endpoints.RESET_NEW_PASSWORD)
    fun reset_new_password(
        @Field("phone") phone: String,
        @Field("reset_token") reset_token: String,
        @Field("new_password") new_password: String
    ): Single<MainResponse>

    @GET(Endpoints.FRIENDS)
    fun get_friends(): Observable<FriendsResponse>

    @GET(Endpoints.TOP20)
    fun getTop(): Observable<Top20ListResponse>

    @GET(Endpoints.TOP_FRIENDS)
    fun getTopFriends(): Observable<TopListResponse>

    @GET(Endpoints.TOP_COUNTRY)
    fun getTopByCountry(
        @Query("country_id") country_id: Int
    ): Observable<TopListResponse>

    @GET(Endpoints.TOP_COUNTRY)
    fun getTopByCity(
        @Query("country_id") country_id: Int,
        @Query("city_id") city_id: Int
    ): Observable<TopListResponse>

    @GET(Endpoints.COUNTRIES)
    fun getCountries(): Observable<Countries>

    @GET(Endpoints.CITIES)
    fun getCitites(
        @Path("id") id: Int
    ): Observable<Cities>

    @GET(Endpoints.INVITE_URL)
    fun invite(): Observable<InviteModel>

    @GET(Endpoints.COUNTRY_BY_ID)
    fun getCountryById(
        @Path("id") id: Int
    ): Observable<CountriesItem>

    @GET(Endpoints.CITY_BY_ID)
    fun getCityById(
        @Path("id1") countryId: Int,
        @Path("id2") cityId: Int
    ): Observable<CitiesItem>

    @GET(Endpoints.FRIENDS)
    fun get_friends2(
        @Query("page") id: Int
    ): Single<PaginationMain>

    @GET("cities_statistic")
    fun statsByCities(
        @Query("page") page: Int,
        @Query("sort_key") sort_key: String
    ): Single<Pagination>

    @GET("course_statistic")
    fun statsByCourses(
        @Query("page") page: Int,
        @Query("sort_key") sort_key: String
    ): Single<Pagination>

    @GET(Endpoints.BLACKLIST)
    fun blackList(): Observable<FriendsResponse>

    @GET(Endpoints.BLACKLIST)
    fun blackList2(@Query("page") id: Int): Single<PaginationMain>

    @GET(Endpoints.STATISTICS)
    fun getStatistics(@Query("user_id") userId: Int): Observable<Response<StatisticsModel>>

    @GET(Endpoints.STATISTICS)
    fun myStatistics(): Observable<Response<StatisticsModel>>

    @GET(Endpoints.FRIENDS_INFO)
    fun get_friend_info(
        @Path("id") id: Int
    ): Observable<Response<JsonObject>>

    /////
    @FormUrlEncoded
    @POST(Endpoints.START_ROUND)
    fun startRound(
        @Path("id") id: Int,
        @Field("category_id") categoryId: Int
    ): Observable<Response<RoundStartModel>>

    @GET(Endpoints.ALL_GAMES)
    fun getGamesList(): Observable<Response<TurnsResponse>>

    @FormUrlEncoded
    @POST(Endpoints.ANSWER_TO_ROUND)
    fun answerToRound(
        @Path("id") roundId: Int,
        @Field("answers[0][question_id]") firstQuestion: Int,
        @Field("answers[0][answer_id]") firstAnswer: Int,
        @Field("answers[1][question_id]") secondQuestion: Int,
        @Field("answers[1][answer_id]") secondAnswer: Int,
        @Field("answers[2][question_id]") thirdQuestion: Int,
        @Field("answers[2][answer_id]") thirdAnswer: Int
    ): Single<Response<JsonObject>>

    @GET(Endpoints.CATEGORIES_RANDOM)
    fun categoriesRandom(): Observable<Response<RandomCategories>>

    @POST(Endpoints.FRIENDS_ADD)
    fun add_friend(
        @Path("id") id: Int
    ): Observable<JsonObject>

    @POST(Endpoints.LOSE_GAME)
    fun loseGame(
        @Path("id") id: Int
    ): Observable<Response<JsonObject>>

    @POST(Endpoints.LOSE_ROUND)
    fun loseRound(
        @Path("id") id: Int
    ): Observable<Response<JsonObject>>

    @POST(Endpoints.START_WITH_RANDOM)
    fun gameWithRandom(): Observable<Response<MainGameResponse>>

    @DELETE(Endpoints.FRIENDS_INFO)
    fun delete_friend(
        @Path("id") id: Int
    ): Observable<JsonObject>

    @POST(Endpoints.SIGNOUT)
    fun log_out(): Single<MainResponse>

    @GET(Endpoints.USERINFO)
    fun get_user_info(): Observable<Response<JsonObject>>

    @GET(Endpoints.SEARCH_USERS)
    fun search_users(
        @Query("text") text: String,
        @Query("page") id: Int
    ): Single<PaginationMain>

    @GET(Endpoints.WEB_VIEW)
    fun getWebView(): Observable<WebViewModel>

    @GET(Endpoints.GAME_BY_ID)
    fun gameById(
        @Path("id") id: Int
    ): Single<Response<MainGameResponse>>

    @POST(Endpoints.CREATE_USER)
    fun create_user(
        @Body loginRequest: LoginRequest
    ): Single<Response<MainResponse>>

    @POST(Endpoints.DELETE_ACCOUNT)
    fun deleteAccount(): Completable

    @GET(Endpoints.DEPARTMENT_LIST)
    fun departmentList(): Single<MutableList<Department>>

    @GET(Endpoints.GROUP_LIST)
    fun groupList(
        @Query("department_id") department_id: Int
    ): Single<MutableList<Group>>

    @GET("course")
    fun getCourses(
        @Query("department_id") department_id: Int
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
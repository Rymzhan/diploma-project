package com.diploma.stats.views.auth.repositories

import com.diploma.stats.global.service.ServerService
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

class UserRepoImpl(
    private val serverService: ServerService
) : UserRepo {
    override fun login(phone: String, password: String, deviceToken: String): Single<Response<MainResponse>> =
        serverService.login(
            LoginRequest(
                phone = phone, password = password, deviceToken = deviceToken
            )
        )

    override fun confirm_registr(phone: String, code: String): Single<MainResponse> =
        serverService.confirm(phone = phone, code = code)

    override fun suggestQuestion(
        email: String,
        id: Int,
        description: String
    ): Single<Response<JsonObject>>  = serverService.suggestQuestion(email,id,description)

    override fun register(phone: String): Single<Response<MainResponse>> =
        serverService.register(phone = phone)

    override fun getCountries(): Observable<Countries> =
        serverService.getCountries()

    override fun getCities(id: Int): Observable<Cities> =
        serverService.getCitites(id)

    override fun getCountryById(id: Int): Observable<CountriesItem> =
        serverService.getCountryById(id)

    override fun getCityById(country_id: Int, city_id: Int): Observable<CitiesItem> =
        serverService.getCityById(countryId = country_id, cityId = city_id)

    override fun invite(): Observable<InviteModel> =
        serverService.invite()

    override fun reset_request(phone: String): Single<MainResponse> =
        serverService.reset_request(phone = phone)

    override fun reset_confirm(phone: String, code: String): Single<MainResponse> =
        serverService.reset_confirm(phone = phone, code = code)

    override fun newGameFriend(id: Int): Single<Response<MainGameResponse>> =
        serverService.newGameFriend(id = id)

    override fun getTop(): Observable<Top20ListResponse> = serverService.getTop()

    override fun getTopFriends(): Observable<TopListResponse> = serverService.getTopFriends()

    /*override fun getMessageSocket(): Flowable<SocketMainResponse> =
        webSocketListener.getMessageSocket()*/

    override fun getTopByCountry(country_id: Int): Observable<TopListResponse> =
        serverService.getTopByCountry(country_id)

    override fun getTopByCity(country_id: Int, city_id: Int): Observable<TopListResponse> =
        serverService.getTopByCity(country_id = country_id, city_id = city_id)

    override fun updateProfile(
        login: RequestBody?,
        first_name: RequestBody?,
        last_name: RequestBody?,
        organization: RequestBody?,
        workplace: RequestBody?,
        country_id: RequestBody?,
        city_id: RequestBody?,
        password: RequestBody?,
        image: MultipartBody.Part?
    ): Single<Response<JsonObject>> = serverService.updateProfile(
        login,
        first_name,
        last_name,
        organization,
        workplace,
        country_id,
        city_id,
        password,
        image
    )

    override fun reset_new_password(
        phone: String,
        reset_token: String,
        new_password: String
    ): Single<MainResponse> = serverService.reset_new_password(
        phone = phone,
        reset_token = reset_token,
        new_password = new_password
    )

    override fun search_users(text: String, token: String, id: Int): Single<PaginationMain> =
        serverService.search_users(text = text, id = id)

    override fun getWebView(): Observable<WebViewModel> =
        serverService.getWebView()

    override fun userStatistics(id: Int): Observable<Response<StatisticsModel>> =
        serverService.getStatistics(id)

    override fun myStats(): Observable<Response<StatisticsModel>> =
        serverService.myStatistics()

    override fun user_info(): Observable<Response<JsonObject>> = serverService.get_user_info()

    override fun friend_info(id: Int): Observable<Response<JsonObject>> =
        serverService.get_friend_info(id = id)

    override fun add_friend(id: Int): Observable<JsonObject> = serverService.add_friend(id = id)

    override fun loseGame(id: Int): Observable<Response<JsonObject>> = serverService.loseGame(id)

    override fun loseRound(id: Int): Observable<Response<JsonObject>> = serverService.loseRound(id)

    override fun startRound(id: Int, categoryId: Int): Observable<Response<RoundStartModel>> =
        serverService.startRound(id = id, categoryId = categoryId)

    override fun gameById(id: Int): Single<Response<MainGameResponse>> =
        serverService.gameById(id)

    override fun randomGame(): Observable<Response<MainGameResponse>> =
        serverService.gameWithRandom()

    override fun answerToRound(
        roundId: Int,
        firstQuestion: Int,
        firstAnswer: Int,
        secondQuestion: Int,
        secondAnswer: Int,
        thirdQuestion: Int,
        thirdAnswer: Int
    ): Single<Response<JsonObject>> = serverService.answerToRound(
        roundId,
        firstQuestion,
        firstAnswer,
        secondQuestion,
        secondAnswer,
        thirdQuestion,
        thirdAnswer
    )

    override fun categoriesRandom(): Observable<Response<RandomCategories>> =
        serverService.categoriesRandom()

    override fun gamesList(): Observable<Response<TurnsResponse>> =
        serverService.getGamesList()

    override fun list_friends(): Observable<FriendsResponse> =
        serverService.get_friends()

    override fun list_friends2(id: Int): Single<PaginationMain> =
        serverService.get_friends2(id)

    override fun statsByCities(page: Int, key: String): Single<Pagination> =
        serverService.statsByCities(page,key)

    override fun statsByCourses(page: Int, key: String): Single<Pagination>  =
        serverService.statsByCourses(page,key)

    override fun blackLis2t(id: Int): Single<PaginationMain> =
        serverService.blackList2(id)

    override fun log_out(): Single<MainResponse> = serverService.log_out()

    override fun blackList(): Observable<FriendsResponse> = serverService.blackList()

    override fun delete_user(id: Int): Observable<JsonObject> = serverService.delete_friend(id)

    override fun blockList(list: MutableList<Int>): Single<Response<JsonObject>> =
        serverService.blockUserList(list = list)

    override fun userById(id: Int): Observable<Response<JsonObject>> =
        serverService.userById(id)

    override fun removeList(list: MutableList<Int>): Single<Response<JsonObject>> =
        serverService.removeList(list = list)

    override fun create_user(
        login: String,
        first_name: String,
        last_name: String,
        organization: String,
        workplace: String,
        country_id: Int,
        city_id: Int,
        phone: String,
        registration_token: String,
        password: String,
        image: String?,
        deviceToken: String
    ): Single<Response<MainResponse>> =
        serverService.create_user(
            LoginRequest(
                login = login,
                firstName = first_name,
                lastName = last_name,
                organization = organization,
                workplace = workplace,
                countryId = country_id,
                cityId = city_id,
                password = password,
                phone = phone,
                registrationToken = registration_token,
                image = image,
                deviceToken = deviceToken
            )
        )

    override fun deleteAccount(): Completable =
        serverService.deleteAccount()

    override fun departmentLis(): Single<MutableList<Department>> =
        serverService.departmentList()

    override fun groupList(dep_id: Int): Single<MutableList<Group>> =
        serverService.groupList(dep_id)

    override fun getCourses(department_id: Int): Single<MutableList<Course>> =
        serverService.getCourses(department_id)

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
}
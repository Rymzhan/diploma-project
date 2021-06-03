package com.diploma.stats.views.auth.interactors

import com.diploma.stats.global.system.SchedulersProvider
import com.diploma.stats.model.auth.login.MainResponse
import com.diploma.stats.model.invite.InviteModel
import com.diploma.stats.model.list.Cities
import com.diploma.stats.model.list.CitiesItem
import com.diploma.stats.model.list.Countries
import com.diploma.stats.model.list.CountriesItem
import com.diploma.stats.model.list.top.TopListResponse
import com.diploma.stats.model.main.friends.DataX
import com.diploma.stats.model.main.friends.FriendsResponse
import com.diploma.stats.model.main.game.categories.RandomCategories
import com.diploma.stats.model.main.game.round.start.RoundStartModel
import com.diploma.stats.model.main.game.start.main.MainGameResponse
import com.diploma.stats.model.main.profile.statistics.StatisticsModel
import com.diploma.stats.model.main.profile.turns.TurnsResponse
import com.diploma.stats.model.main.search.SearchUser
import com.diploma.stats.model.web_view.WebViewModel
import com.diploma.stats.views.auth.repositories.UserRepo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.diploma.stats.model.department.dep_list.*
import com.diploma.stats.model.department.response.DepartmentResponse
import com.diploma.stats.model.list.top.Top20ListResponse
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
    fun login(
        phone: String,
        password: String,
        deviceToken: String
    ): Single<Response<MainResponse>> =
        userRepository.login(phone = phone, password = password, deviceToken = deviceToken)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun register(
        phone: String
    ): Single<Response<MainResponse>> = userRepository.register(phone = phone)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun confirm_registr(phone: String, code: String): Single<MainResponse> =
        userRepository.confirm_registr(phone = phone, code = code)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCountries(): Observable<Countries> =
        userRepository.getCountries()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCountryById(id: Int): Observable<CountriesItem> =
        userRepository.getCountryById(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCityById(country_id: Int, city_id: Int): Observable<CitiesItem> =
        userRepository.getCityById(country_id = country_id, city_id = city_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun invite(): Observable<InviteModel> = userRepository.invite()
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun suggestQuestion(email: String, id: Int, desc: String): Single<Response<JsonObject>> =
        userRepository.suggestQuestion(email, id, desc)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCities(id: Int): Observable<Cities> =
        userRepository.getCities(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTop(): Observable<Top20ListResponse> =
        userRepository.getTop()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTopFriends(): Observable<TopListResponse> =
        userRepository.getTopFriends()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTopByCountry(country_id: Int): Observable<TopListResponse> =
        userRepository.getTopByCountry(country_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getTopByCity(country_id: Int, city_id: Int): Observable<TopListResponse> =
        userRepository.getTopByCity(country_id = country_id, city_id = city_id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun list_friends(): Observable<FriendsResponse> = userRepository.list_friends()
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun blacklist2(id: Int): Single<List<DataX>> = userRepository.blackLis2t(id)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
        .map {
            val typeToken = object : TypeToken<List<DataX>>() {}.type
            return@map Gson().fromJson<List<DataX>>(it.data.data, typeToken)
        }

    fun loseGame(id: Int): Observable<Response<JsonObject>> = userRepository.loseGame(id)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun loseRound(id: Int): Observable<Response<JsonObject>> = userRepository.loseRound(id)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun reset_request(phone: String): Single<MainResponse> =
        userRepository.reset_request(phone = phone)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun userStatistics(id: Int): Observable<Response<StatisticsModel>> =
        userRepository.userStatistics(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    /*   fun getMessageSocket(): Flowable<SocketMainResponse> = userRepository.getMessageSocket()
           .subscribeOn(schedulersProvider.io())
           .observeOn(schedulersProvider.ui())*/

    fun myStats(): Observable<Response<StatisticsModel>> =
        userRepository.myStats()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun newGameFriend(id: Int): Single<Response<MainGameResponse>> =
        userRepository.newGameFriend(id = id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun reset_confirm(phone: String, code: String): Single<MainResponse> =
        userRepository.reset_confirm(phone = phone, code = code)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun answerToRound(
        roundId: Int,
        firstQuestion: Int,
        firstAnswer: Int,
        secondQuestion: Int,
        secondAnswer: Int,
        thirdQuestion: Int,
        thirdAnswer: Int
    ): Single<Response<JsonObject>> =
        userRepository.answerToRound(
            roundId,
            firstQuestion,
            firstAnswer,
            secondQuestion,
            secondAnswer,
            thirdQuestion,
            thirdAnswer
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun reset_new_password(
        phone: String,
        reset_token: String,
        new_password: String
    ): Single<MainResponse> =
        userRepository.reset_new_password(
            phone = phone,
            reset_token = reset_token,
            new_password = new_password
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun list_friends2(id: Int): Single<List<DataX>> = userRepository.list_friends2(id)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
        .map {
            val typeToken = object : TypeToken<List<DataX>>() {}.type
            return@map Gson().fromJson<List<DataX>>(it.data.data, typeToken)
        }

    fun statsByCities(key: String,id: Int): Single<List<StudentCityResult>> = userRepository.statsByCities(id,key)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
        .map {
            val typeToken = object : TypeToken<List<StudentCityResult>>() {}.type
            return@map Gson().fromJson<List<StudentCityResult>>(it.data, typeToken)
        }

    fun statsByCourses(key: String,id: Int): Single<List<CourseResult>> = userRepository.statsByCourses(id,key)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())
        .map {
            val typeToken = object : TypeToken<List<CourseResult>>() {}.type
            return@map Gson().fromJson<List<CourseResult>>(it.data, typeToken)
        }

    fun search_user(token: String, text: String, id: Int): Single<List<SearchUser>> =
        userRepository.search_users(token = token, text = text, id = id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .map {
                val typeToken = object : TypeToken<List<SearchUser>>() {}.type
                return@map Gson().fromJson<List<SearchUser>>(it.data.data, typeToken)
            }

    fun user_info(): Observable<Response<JsonObject>> =
        userRepository.user_info()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun gameById(id: Int): Single<Response<MainGameResponse>> =
        userRepository.gameById(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun gameWithRandom(): Observable<Response<MainGameResponse>> =
        userRepository.randomGame()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())


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
    ): Single<Response<JsonObject>> = userRepository.updateProfile(
        login,
        first_name,
        last_name,
        organization,
        workplace,
        country_id,
        city_id,
        password,
        image
    ).subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun getWebView(): Observable<WebViewModel> =
        userRepository.getWebView()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun friend_info(id: Int): Observable<Response<JsonObject>> =
        userRepository.friend_info(id = id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun startRound(id: Int, categoryId: Int): Observable<Response<RoundStartModel>> =
        userRepository.startRound(id = id, categoryId = categoryId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun categoriesRandom(): Observable<Response<RandomCategories>> =
        userRepository.categoriesRandom()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun blackList(): Observable<FriendsResponse> =
        userRepository.blackList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun add_friend(id: Int): Observable<JsonObject> =
        userRepository.add_friend(id = id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getGamesList(): Observable<Response<TurnsResponse>> =
        userRepository.gamesList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun log_out(): Single<MainResponse> = userRepository.log_out()
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun blockList(list: MutableList<Int>): Single<Response<JsonObject>> =
        userRepository.blockList(list)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun userById(id: Int): Observable<Response<JsonObject>> =
        userRepository.userById(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun removeList(list: MutableList<Int>): Single<Response<JsonObject>> =
        userRepository.removeList(list)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun delete_user(id: Int): Observable<JsonObject> = userRepository.delete_user(id)
        .subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

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
    ): Single<Response<MainResponse>> = userRepository.create_user(
        login = login,
        first_name = first_name,
        last_name = last_name,
        organization = organization,
        workplace = workplace,
        country_id = country_id,
        city_id = city_id,
        password = password,
        phone = phone,
        registration_token = registration_token,
        image = image,
        deviceToken = deviceToken
    ).subscribeOn(schedulersProvider.io())
        .observeOn(schedulersProvider.ui())

    fun deleteAccount(): Completable =
        userRepository.deleteAccount()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

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
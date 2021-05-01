package com.diploma.stats.views.auth.presenters.registration.third

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.utils.LocalStorage
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class RegThirdPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<RegThirdView>() {
    val TAG = "RegSecondPresenter"

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
    ) {
        userInteractor.create_user(
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
        ).subscribe({
            if (it.code() == 200) {
                val user: User? = it.body()?.data?.user
                val token: String = it.body()?.data?.access_token!!
                if (user != null) {
                    LocalStorage.setName(user.first_name)
                    LocalStorage.setID(user.id)
                    LocalStorage.setSurname(user.last_name)
                    LocalStorage.setLogin(user.login)
                    LocalStorage.setPassword(password)
                    LocalStorage.setUserScore(user.scores)
                    LocalStorage.setPhone(user.phone)
                    LocalStorage.setAccessToken(token)
                    if (!user.image.equals(null)) {
                        LocalStorage.setImage(user.image!!)
                    }
                    inviteLink()
                }
            }
            if (it.code() == 422) {
                viewState.showError("Введенный логин уже занят!")
            }
        }, {
            it.printStackTrace()
            viewState.showError("Ошибка соединения!")
        }).connect()
    }

    fun getCountries() {
        userInteractor.getCountries().subscribe({
            viewState.bindCountries(it)
        }, {
            it.printStackTrace()
        }).connect()
    }

    fun getCities(id: Int) {
        userInteractor.getCities(id).subscribe({
            viewState.bindCities(it)
        }, {
            it.printStackTrace()
        }).connect()
    }

    private fun inviteLink() {
        userInteractor.invite().subscribe({
            LocalStorage.setInvite("Привет! Сразись со мной в «Bosch - Борьба Умов»! Мой ник @" + LocalStorage.getLogin() + ". Скачать бесплатно: " + it.android + "\n" + it.ios)
            viewState.goToProfile()
        }, {
            viewState.showError("Ошибка соединения")
        }).connect()
    }


}
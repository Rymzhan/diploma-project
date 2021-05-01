package com.diploma.stats.views.auth.presenters.login

import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.utils.LocalStorage
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.views.auth.interactors.UserInteractor

@InjectViewState
class LoginPresenter(
    private val userInteractor: UserInteractor
) : BasePresenter<LoginView>() {

    val TAG = "LoginPresenter"
    fun login(phone: String, password: String, deviceToken: String) {
        userInteractor.login(phone = phone, password = password, deviceToken = deviceToken)
            .subscribe({
                if (it.code() == 200) {
                    val user: User? = it.body()?.data?.user
                    val token: String = it.body()?.data?.access_token!!
                    if (user != null) {
                        LocalStorage.setName(user.first_name)
                        LocalStorage.setID(user.id)
                        LocalStorage.setPush(true)
                        LocalStorage.setSound(true)
                        LocalStorage.setSurname(user.last_name)
                        LocalStorage.setLogin(user.login)
                        LocalStorage.setPassword(password)
                        LocalStorage.setUserScore(user.scores)
                        LocalStorage.setPhone(user.phone.toString())
                        LocalStorage.setAccessToken(token)
                        if (!user.image.equals(null)) {
                            LocalStorage.setImage(user.image!!)
                        }
                        inviteLink()
                    }
                }
                if (it.code() == 401) {
                    viewState.showError("Не авторизован!")
                }

            }, {
                it.printStackTrace()
                viewState.showError("Не авторизован!")
                //viewState.showError()
                //Log.d(TAG, it.toString())
            }).connect()
    }

    private fun inviteLink() {
        userInteractor.invite().subscribe({
            LocalStorage.setInvite("Привет! Сразись со мной в «Bosch - Борьба Умов»! Мой ник @" + LocalStorage.getLogin() + ". Скачать бесплатно: " + it.android + "\n" + it.ios)
            viewState.openProfileFrag()
        }, {
            viewState.showError(it.message.toString())
        }).connect()
    }

}

package com.diploma.stats.views.main.presentation.profile.edit

import android.graphics.Bitmap
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.diploma.stats.global.presentation.BasePresenter
import com.diploma.stats.global.service.ApiModelHelper
import com.diploma.stats.global.utils.BitmapHelper
import com.diploma.stats.global.utils.LocalStorage
import com.diploma.stats.model.auth.login.User
import com.diploma.stats.views.auth.interactors.UserInteractor
import okhttp3.MediaType
import okhttp3.RequestBody

@InjectViewState
class ProfileEditPresenter(private val userInteractor: UserInteractor) :
    BasePresenter<ProfileEditView>() {
    val TAG = "ProfileEditPresenter"
    val type = "text/plain"
    private var photo: Bitmap? = null
    fun setPhoto(image: Bitmap?) {
        Log.i("bitmap", "bitmap not null setPhoto")
        photo = image
    }

    fun updateProfile(
        login: String,
        first_name: String,
        last_name: String,
        organization: String,
        workplace: String,
        country_id: Int,
        city_id: Int,
        password: String?
    ) {
        userInteractor.updateProfile(
            RequestBody.create(MediaType.parse(type), login),
            RequestBody.create(MediaType.parse(type), first_name),
            RequestBody.create(MediaType.parse(type), last_name),
            RequestBody.create(MediaType.parse(type), organization),
            RequestBody.create(MediaType.parse(type), workplace),
            RequestBody.create(MediaType.parse(type), country_id.toString()),
            RequestBody.create(MediaType.parse(type), city_id.toString()),
            if (password == null) null else RequestBody.create(MediaType.parse(type), password),
            image = BitmapHelper.getFileDataFromBitmap("image", photo)
        ).subscribe({
            if (it.code() == 200) {
                val user: User = ApiModelHelper.getObject(it.body()!!, User::class.java)
                Log.d("loginCheck", user.login)
                LocalStorage.setName(user.first_name.toString())
                LocalStorage.setSurname(user.last_name)
                LocalStorage.setLogin(user.login)
                password?.let { LocalStorage.setPassword(password) }
                if (!user.image.equals(null)) {
                    LocalStorage.setImage(user.image!!)
                }
                viewState.openProfileFrag()
            }
            if (it.code() == 422) {
                viewState.showError("Введенный логин уже занят!")
            }
        }, {
            //viewState.showError()
            viewState.showError("Ошибка соединения!")
            it.printStackTrace()
        }).connect()
    }

    fun getCountries() {
        userInteractor.getCountries().subscribe({
            viewState.bindCountry(it)
        }, {
            viewState.showError("Ошибка соединения!")
            it.printStackTrace()
        }).connect()
    }

    fun getCities(id: Int) {
        userInteractor.getCities(id).subscribe({
            viewState.bindCity(it)
        }, {
            it.printStackTrace()
            viewState.showError("Ошибка соединения!")
        }).connect()
    }
}
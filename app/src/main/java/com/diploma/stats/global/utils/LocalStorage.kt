package com.diploma.stats.global.utils

import com.pixplicity.easyprefs.library.Prefs

object LocalStorage{
    const val PREF_NO_VAL = "no_val"
    const val PREF_DEFAULT_LANGUAGE = "ru"

    private const val PREF_ACCESS_TOKEN = "access_token"
    private const val PREF_FIRST_TIME_LAUNCHED = "first_time_launched"
    private const val PREF_PUSH = "push_notification"
    private const val PREF_SOUND = "sound_notification"
    private const val PREF_LANGUAGE = "LANGUAGE"
    private const val PREF_PASSWORD ="PREF_PASSWORD"
    private const val PREF_NAME = "PREF_NAME"
    private const val PREF_SURNAME = "PREF_SURNAME"
    private const val PREF_LOGIN = "PREF_LOGIN"
    private const val PREF_PHONE= "PREF_PHONE"
    private const val PREF_EMAIL= "PREF_EMAIL"
    private const val PREF_REGISTER_PHONE = "PREF_REGISTER_PHONE"
    private const val PREF_EXECUTER_ID = "EXECUTER_ID"
    private const val PREF_AUTHOR= "PREF_AUTHOR"
    private const val PREF_REG_TOKEN = "REG_TOKEN"
    private const val PREF_RESET_TOKEN = "RESET_TOKEN"
    private const val PREF_USER_IMAGE = "USER_IMAGE"
    private const val PREF_USER_SCORE = "USER_SCORE"
    private const val PREF_INVITE = "PREF_INVITE"

    fun setAccessToken(accessToken: String) = Prefs.putString(PREF_ACCESS_TOKEN, accessToken)

    fun getAccessToken(): String = Prefs.getString(PREF_ACCESS_TOKEN, "tokasen")
    //fun getAccessToken(): String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xODUuMTI1LjkxLjIyXC9hcGlcL3YxXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxNTI3NjkzNCwiZXhwIjoxNjE2NDg2NTM0LCJuYmYiOjE2MTUyNzY5MzQsImp0aSI6IlZYdGljZFUzcWhnT1dzUlciLCJzdWIiOjk5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.bt0LN_eYC37dI1WckjJ5ZJ6MhDfL19X2afdY2OAeBMQ"

    fun setID(executer_id : Int) = Prefs.putInt(PREF_EXECUTER_ID, executer_id)

    fun getID(): Int= Prefs.getInt(PREF_EXECUTER_ID, -1)
    //fun getID(): Int = 99j

    fun setName(name : String) = Prefs.putString(PREF_NAME, name)

    fun getName(): String = Prefs.getString(PREF_NAME, "1")

    fun setInvite(link: String) = Prefs.putString(PREF_INVITE, link)

    fun getInvite(): String = Prefs.getString(PREF_INVITE,"1")

    fun setPassword(password : String) = Prefs.putString(PREF_PASSWORD, password)

    fun getPassword(): String = Prefs.getString(PREF_PASSWORD, null)

    fun setSurname(surname : String) = Prefs.putString(PREF_SURNAME, surname)

    fun getSurname(): String = Prefs.getString(PREF_SURNAME, "1")

    fun setLogin(login : String) = Prefs.putString(PREF_LOGIN, login)

    fun getLogin(): String = Prefs.getString(PREF_LOGIN, "1")

    fun setImage(url : String) = Prefs.putString(PREF_USER_IMAGE, url)

    fun getImage(): String = Prefs.getString(PREF_USER_IMAGE, "1")

    fun setUserScore(points : Int) = Prefs.putInt(PREF_USER_SCORE, points)

    fun getUserScore(): Int = Prefs.getInt(PREF_USER_SCORE, -1)

    fun setPhone(phone : String) = Prefs.putString(PREF_PHONE, phone)

    fun getPhone(): String = Prefs.getString(PREF_PHONE, "1")

    fun setMail(Mail : String) = Prefs.putString(PREF_EMAIL, Mail)

    fun getMail(): String = Prefs.getString(PREF_EMAIL, "1@mail.ru")

    fun setPush(push: Boolean) = Prefs.putBoolean(PREF_PUSH, push)

    fun getPush() : Boolean = Prefs.getBoolean(PREF_PUSH, true)

    fun setSound(sound: Boolean) = Prefs.putBoolean(PREF_SOUND, sound)

    fun getSound() : Boolean = Prefs.getBoolean(PREF_SOUND, true)

    fun setFirstTimeLaunched(isFirstTime: Boolean) = Prefs.putBoolean(PREF_FIRST_TIME_LAUNCHED, isFirstTime)

    fun isFirstTimeLaunched(): Boolean = Prefs.getBoolean(PREF_FIRST_TIME_LAUNCHED, true)

    fun deleteStorageDATA() = Prefs.edit().clear().apply()
}
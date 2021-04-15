package com.thousand.bosch.global.service


import com.thousand.bosch.global.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object ApiModelHelper{

    internal fun <T> getObject(jsonObject: JsonObject, clazz: Class<T>): T{
        return Gson().fromJson(jsonObject.getAsJsonObject(AppConstants.DATA).toString(), clazz)
    }

    internal fun <T> getArray(jsonObject: JsonObject, dataClass: Class<T>): MutableList<T> {
        return Gson().fromJson(
            jsonObject.getAsJsonArray(AppConstants.DATA).toString(),
            getType(MutableList::class.java, dataClass)
        )
    }

    internal fun getErrorMessage(response: Response<JsonObject>): String {
        return JsonParser()
            .parse(
                response.errorBody()!!
                    .string()
            )
            .asJsonObject
            .get("message")
            .asString
    }

    internal fun getErrorMessage(throwable: Throwable): String {
        var message = ""

        if (throwable is HttpException){
            val body = throwable.response()?.errorBody()
            val str: String? = body?.string()
           // val retrofitError = Gson().fromJson(str, RetrofitError::class.java)

            if (str != null) {
                message = str
            }
        }

        return message
    }

    internal fun getResPagesSize(res: JsonObject): Int {
        return res.get(AppConstants.DATA).asJsonObject.get(AppConstants.COUNT_PAGES).asInt
    }

    internal fun <T> getPageArray(jsonObject: JsonObject, dataClass: Class<T>): MutableList<T> {
        return Gson().fromJson(
            jsonObject.getAsJsonObject(AppConstants.DATA).getAsJsonArray(AppConstants.DATA).toString(), getType(MutableList::class.java, dataClass)
        )
    }

    internal fun getErrorStatusCode(throwable: Throwable): Int{
        var statusCode = 0

        if (throwable is HttpException){
            val body = throwable.response()?.errorBody()
            val str: String = body?.string().toString()
            body?.close()
        }

        return statusCode
    }

    fun getType(cll: Class<*>, param: Class<*>): Type{
        return object : ParameterizedType {
            override fun getRawType(): Type {
                return cll
            }

            override fun getOwnerType(): Type? {
                return null
            }

            override fun getActualTypeArguments(): Array<Type> {
                return Array(1){param}
            }

        }
    }

    data class RetrofitError(
        @Expose
        @SerializedName("statusCode")
        val statusCode: Int = 0,
        @Expose
        @SerializedName("message")
        val message: String = "")
}
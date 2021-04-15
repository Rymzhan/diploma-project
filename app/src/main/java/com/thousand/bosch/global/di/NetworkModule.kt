package com.thousand.bosch.global.di

import com.thousand.bosch.BuildConfig
import com.thousand.bosch.global.di.ServiceProperties.AUTH_HEADER
import com.thousand.bosch.global.di.ServiceProperties.SERVER_URL
import com.thousand.bosch.global.di.ServiceProperties.WEB_SOCKET_TOKEN
import com.thousand.bosch.global.di.ServiceProperties.WEB_SOCKET_URL
import com.thousand.bosch.global.service.ServerService
import com.thousand.bosch.global.utils.LocalStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<ServerService>(get(), SERVER_URL) }
 }


object ServiceProperties {
    const val SERVER_URL = "http://185.125.91.22/api/v1/"
    const val AUTH_HEADER = "Authorization"
    const val WEB_SOCKET_URL = "ws://185.125.91.22:8080"
    const val WEB_SOCKET_TOKEN = "?token="
}

fun createOkHttpClient(): OkHttpClient {

    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.connectTimeout(5, TimeUnit.MINUTES)
    okHttpClientBuilder.callTimeout(5, TimeUnit.MINUTES)
    okHttpClientBuilder.readTimeout(5, TimeUnit.MINUTES)
    okHttpClientBuilder.writeTimeout(5, TimeUnit.MINUTES)

    okHttpClientBuilder.addInterceptor { chain ->
        var request = chain.request()
        val url = request.url().newBuilder()
        val authToken = "Bearer " + LocalStorage.getAccessToken()


        request = request.newBuilder()
            .addHeader(AUTH_HEADER, authToken)
            .url(url.build())
            .build()
        chain.proceed(request)
    }

    if (BuildConfig.DEBUG) {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.apply {
            addInterceptor(loggingInterceptor)
        }

    }

    Timber.d("WebSocketUrl 1: ${WEB_SOCKET_URL + WEB_SOCKET_TOKEN + LocalStorage.getAccessToken()}")

    return okHttpClientBuilder.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}



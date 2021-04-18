package com.thousand.bosch.global.di

import com.thousand.bosch.BuildConfig
import com.thousand.bosch.global.di.ServiceProperties.SERVER_URL
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
    const val SERVER_URL = "http://46.101.193.42/api/"
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


        request = request.newBuilder()
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



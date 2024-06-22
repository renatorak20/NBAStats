package com.renato.nbastats.data.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit

class Network {

    private val service: NBAStatsService
    private val baseURL = "https://nbapi.fly.dev/"

    fun getService(): NBAStatsService {
        return service
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        val httpClient =
            OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        service = retrofit.create(NBAStatsService::class.java)
    }
}
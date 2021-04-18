package com.example.cockandtail.UIdata

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
object APImodule {

    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    @Provides
    @Singleton
    fun getInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("API", message)
    }.setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun createGson(): Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
            .enableComplexMapKeySerialization()
            .create()

    @Provides
    @Singleton
    fun createOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun buildRetrofit(
            okhttpClient: OkHttpClient,
            gson: Gson,
    ): Retrofit = Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)



}
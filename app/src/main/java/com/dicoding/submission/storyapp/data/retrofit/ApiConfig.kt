package com.dicoding.submission.storyapp.data.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(token: String? = null): ApiService {
        // Menambahkan logging interceptor untuk melihat request dan response API
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Menambahkan authInterceptor untuk menambahkan header Authorization dengan token
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token") // Menambahkan Bearer token ke header request
                .build()
            chain.proceed(requestHeaders)
        }

        // Membuat OkHttpClient dan menambah interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        // Membuat Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/") // URL Base API
            .addConverterFactory(GsonConverterFactory.create()) // Converter untuk mengubah response JSON ke objek
            .client(client) // Menambahkan OkHttpClient
            .build()

        // Mengembalikan instance ApiService untuk komunikasi API
        return retrofit.create(ApiService::class.java)
    }
}


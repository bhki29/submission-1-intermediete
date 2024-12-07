package com.dicoding.submission.storyapp.data.repository

import com.dicoding.submission.storyapp.data.response.LoginResponse
import com.dicoding.submission.storyapp.data.response.RegisterResponse
import com.dicoding.submission.storyapp.data.retrofit.ApiConfig

class AuthRepository {

    private val apiService = ApiConfig.getApiService()  // Menggunakan ApiConfig untuk mendapatkan ApiService

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }
}
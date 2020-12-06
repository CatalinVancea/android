package com.ubb_pdm.catalin_vancea.auth.data.remote

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.ubb_pdm.catalin_vancea.auth.data.TokenHolder
import com.ubb_pdm.catalin_vancea.auth.data.User
import com.ubb_pdm.catalin_vancea.core.Api
import com.ubb_pdm.catalin_vancea.core.Result


object RemoteAuthDataSource {
    interface AuthService {
        @Headers("Content-Type: application/json")
        @POST("/api/auth/login")
        suspend fun login(@Body user: User): TokenHolder
    }

    private val authService: AuthService = Api.retrofit.create(AuthService::class.java)

    suspend fun login(user: User): Result<TokenHolder> {
        try {
            return Result.Success(authService.login(user))
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}


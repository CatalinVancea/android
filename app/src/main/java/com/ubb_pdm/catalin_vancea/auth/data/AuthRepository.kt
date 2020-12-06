package com.ubb_pdm.catalin_vancea.auth.data

import android.content.Context
import android.content.SharedPreferences
import com.ubb_pdm.catalin_vancea.auth.data.remote.RemoteAuthDataSource
import com.ubb_pdm.catalin_vancea.core.Api
import com.ubb_pdm.catalin_vancea.core.Result

object AuthRepository {

    var prefs: SharedPreferences? = null

    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        Api.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String, context: Context): Result<TokenHolder> {
        val user = User(username, password)
        val result = RemoteAuthDataSource.login(user)
        if (result is Result.Success<TokenHolder>) {
            setLoggedInUser(user, result.data, context)
        }

        return result
    }

    private fun setLoggedInUser(user: User, tokenHolder: TokenHolder, context: Context) {
        AuthRepository.user = user
        Api.tokenInterceptor.token = tokenHolder.token

        //todo
        if (prefs == null)
            prefs = context.getSharedPreferences("com.ubb_pdm.catalin_vancea", Context.MODE_PRIVATE)
        val editor = prefs!!.edit()
        editor.putString("token", tokenHolder.token)
        editor.apply()
    }
}

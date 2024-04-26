package com.example.coroutinesplayground.login.data.repository

import com.example.coroutinesplayground.login.data.network.GetAuthStateRequest
import com.example.coroutinesplayground.login.data.model.AuthState
import com.example.coroutinesplayground.login.domain.model.UserCredentials

/**
 * Repository that provides auth state
 */
interface AuthLoginRepository {
    suspend fun authenticate(userData: UserCredentials): Result<AuthState>
}

class AuthLoginRepositoryImpl (
    private val getAuthStateRequestCall: GetAuthStateRequest
): AuthLoginRepository {
    override suspend fun authenticate(userData: UserCredentials): Result<AuthState> {

        if (userData.username == "" || userData.password == "") {
            return Result.failure(Exception("Empty credentials"))
        }

        return getAuthStateRequestCall.execute(userData.username, userData.password)
    }

}
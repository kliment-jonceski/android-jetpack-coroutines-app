package com.example.coroutinesplayground.login.domain

import android.service.autofill.UserData
import com.example.coroutinesplayground.login.domain.data.AuthLoginRepository
import com.example.coroutinesplayground.login.domain.model.AuthState
import com.example.coroutinesplayground.login.domain.model.UserCredentials
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LoginUseCase (
    private val authNetworkRepository: AuthLoginRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend fun login(username: String, password: String) : Result<AuthState> {
//        return withContext(dispatcher) {
//            delay(1500)
//            return@withContext if (password == "1234")  {
//                Result.success(200)
//            } else {
//                Result.failure(Throwable("Wrong credentials"))
//            }
//        }
        return authNetworkRepository.authenticate(UserCredentials(username = username, password = password))
    }
}
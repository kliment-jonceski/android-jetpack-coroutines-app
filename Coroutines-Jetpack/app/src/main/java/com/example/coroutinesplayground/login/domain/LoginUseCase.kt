package com.example.coroutinesplayground.login.domain

import com.example.coroutinesplayground.login.data.repository.AuthLoginRepository
import com.example.coroutinesplayground.login.data.model.AuthState
import com.example.coroutinesplayground.login.domain.model.UserCredentials
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginUseCase (
    private val authNetworkRepository: AuthLoginRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(username: String, password: String)  =
        withContext(dispatcher) {
            authNetworkRepository.authenticate(UserCredentials(username = username, password = password))
        }
}
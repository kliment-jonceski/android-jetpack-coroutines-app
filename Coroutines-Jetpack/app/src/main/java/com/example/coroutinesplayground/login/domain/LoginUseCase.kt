package com.example.coroutinesplayground.login.domain

import com.example.coroutinesplayground.login.data.repository.AuthLoginRepository
import com.example.coroutinesplayground.login.domain.model.UserCredentials
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase (
    private val authNetworkRepository: AuthLoginRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(userCredentials: UserCredentials)  =
        withContext(dispatcher) {
            authNetworkRepository.authenticate(userCredentials)
        }
}
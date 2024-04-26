package com.example.coroutinesplayground.login.di

import com.example.coroutinesplayground.login.domain.LoginUseCase
import com.example.coroutinesplayground.login.data.repository.AuthLoginRepository
import com.example.coroutinesplayground.login.data.repository.AuthLoginRepositoryImpl
import com.example.coroutinesplayground.login.data.network.GetAuthStateRequest
import com.example.coroutinesplayground.login.view.viewmodel.LoginViewModel
import com.example.coroutinesplayground.playground.PlaygroundActivity1ViewModel
import org.koin.dsl.module

/**
 * Provides a koin module for the login feature.
 */
val loginModule = module {
    single { GetAuthStateRequest() }
    single <AuthLoginRepository> { AuthLoginRepositoryImpl(get()) }
    single { LoginUseCase(get()) }
    single { LoginViewModel(get()) }
    single { PlaygroundActivity1ViewModel() }
}
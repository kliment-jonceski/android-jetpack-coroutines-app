package com.example.coroutinesplayground.shared.di

import com.example.coroutinesplayground.login.di.loginModule
import com.example.coroutinesplayground.main.di.mainModule
import com.example.coroutinesplayground.shared.networking.di.retrofitModule

val appModules = listOf(
    loginModule,
    retrofitModule,
    mainModule
)
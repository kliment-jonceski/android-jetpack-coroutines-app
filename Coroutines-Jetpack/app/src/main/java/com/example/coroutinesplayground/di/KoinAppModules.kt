package com.example.coroutinesplayground.di

import com.example.coroutinesplayground.login.di.loginModule
import com.example.coroutinesplayground.main.di.mainModule
import com.example.coroutinesplayground.networking.di.retrofitModule

val appModules = listOf(
    loginModule,
    retrofitModule,
    mainModule
)
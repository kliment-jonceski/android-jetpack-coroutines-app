package com.example.coroutinesplayground.networking.di

import com.example.coroutinesplayground.networking.NetworkHelper
import org.koin.dsl.module

val retrofitModule = module {
    single { NetworkHelper.getInstance() }
}
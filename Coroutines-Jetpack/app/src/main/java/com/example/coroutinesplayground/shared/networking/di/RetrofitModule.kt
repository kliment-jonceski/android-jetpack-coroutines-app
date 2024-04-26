package com.example.coroutinesplayground.shared.networking.di

import com.example.coroutinesplayground.shared.networking.NetworkHelper
import org.koin.dsl.module

val retrofitModule = module {
    single { NetworkHelper.getInstance() }
}
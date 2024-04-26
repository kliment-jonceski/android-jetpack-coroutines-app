package com.example.coroutinesplayground.login.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.coroutinesplayground.main.ui.MainActivity
import com.example.coroutinesplayground.ui.theme.CoroutinesPlaygroundTheme
import org.koin.android.ext.android.inject

class LoginActivity: ComponentActivity() {
    private val loginViewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesPlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by loginViewModel.state.collectAsState()
                    if (uiState == LoginViewModel.LoginState.Success) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }
                    LoginComponent(loginViewModel = loginViewModel, uiState)
                }
            }
        }
    }
}
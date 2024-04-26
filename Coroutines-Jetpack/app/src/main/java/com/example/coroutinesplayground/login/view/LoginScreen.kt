package com.example.coroutinesplayground.login.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LoginComponent(
    loginViewModel: LoginViewModel,
    uiState: LoginViewModel.LoginState
) {
    var credentialsState by remember {
        mutableStateOf(LoginCredentials("", ""))
    }

    Column (modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserNameComponent(
            value = credentialsState.username,
            onValueChange = { data ->
                credentialsState = credentialsState.copy(
                    username = data,
                    password = credentialsState.password
                )
            }
        )

        PasswordComponent(
            value = credentialsState.password,
            onValueChange = {
                credentialsState = credentialsState.copy(
                    username = credentialsState.username,
                    password = it
                )
            }
        )
        if (uiState == LoginViewModel.LoginState.Failure) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                color = Color.Red ,
                text = "Wrong credentials. Try again.")
        }
        LoginButton(
            onclick = {
                loginViewModel.onEvent(
                    LoginViewModel.Event.OnSubmitLogin(
                        credentialsState.username,
                        credentialsState.password)
                )
            })
    }
    if (uiState == LoginViewModel.LoginState.InProgress) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(32.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameComponent(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "username") }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordComponent(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "password") }
    )
}

@Composable
fun LoginButton(
    onclick: () -> Unit
) {
    Button(onClick = onclick,
        modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Login",
            modifier = Modifier.padding(horizontal = 16.dp))
    }
}

data class LoginCredentials(
    var username: String,
    var password: String = ""
)
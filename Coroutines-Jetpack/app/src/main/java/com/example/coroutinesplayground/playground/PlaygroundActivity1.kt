package com.example.coroutinesplayground.playground

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutinesplayground.login.view.LoginActivity
import com.example.coroutinesplayground.shared.ui.theme.CoroutinesPlaygroundTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.koin.android.ext.android.inject
import timber.log.Timber

class PlaygroundActivity1 : ComponentActivity() {
    private val viewModel: PlaygroundActivity1ViewModel by inject()
    private var localText = "kkk"
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                Timber.d("Inside coroutine lunch")
                val a = withTimeout(5000) { viewModel.loadData1() }
                Timber.d("After coroutine lunch")
                //localTextState = a
            }
        }
        setContent {
            CoroutinesPlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var localTextState: String by remember { mutableStateOf("Initial") }
                    Column {
                        Text(text = "Playgorund 111")
                        Text(text = localTextState)
                        Button(onClick = {
                            startActivity(Intent(this@PlaygroundActivity1, LoginActivity::class.java))
                        }) {
                            Text(text = "Start new activity")
                        }
                    }
                    //Timber.d("before coroutine lanuch")
                }
            }
        }
    }
}
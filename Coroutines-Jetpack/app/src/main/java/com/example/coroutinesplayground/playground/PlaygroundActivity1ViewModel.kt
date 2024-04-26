package com.example.coroutinesplayground.playground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaygroundActivity1ViewModel: ViewModel() {

    fun onEvent (event: Event) {
        viewModelScope.launch {
            when (event) {
                Event.DoSomething -> loadData()
            }
        }
    }

    private suspend fun loadData () {

    }

    suspend fun loadData1(): String {
        delay(4000)
        return "Initiall"
    }

    sealed class PlaygroundViewState {
        object Loading: PlaygroundViewState()
        data class OnLoad(val data: String) : PlaygroundViewState()
        data class OnError(val error: String) : PlaygroundViewState()
    }

    sealed class Event {
        object DoSomething : Event()
    }
}
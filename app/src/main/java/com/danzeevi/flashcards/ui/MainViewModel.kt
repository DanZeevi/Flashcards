package com.danzeevi.flashcards.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danzeevi.flashcards.common.AppEvent
import com.danzeevi.flashcards.common.EventBus
import com.danzeevi.flashcards.common.collectEvents
import kotlinx.coroutines.launch

enum class Screen {
    List, Test
}

class MainViewModel(private val eventBus: EventBus) : ViewModel() {
    private val _currentScreen = MutableLiveData<Screen>(Screen.List)
    val currentScreen: LiveData<Screen> = _currentScreen

    init {
        collectEvents(eventBus) { event ->
            when (event) {
                is AppEvent.DeepLinkAddValue -> {
                    _currentScreen.value = Screen.List
                }

                AppEvent.EmptyEvent -> {} // No-op
            }
        }
    }

    fun postEvent(event: AppEvent) {
        viewModelScope.launch {
            eventBus.post(event)
        }
    }

    fun setScreen(screen: Screen) {
        _currentScreen.value = screen
    }

}
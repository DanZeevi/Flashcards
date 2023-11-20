package com.danzeevi.flashcards.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class Screen {
    List, Test
}

class MainViewModel: ViewModel() {
    private val _currentScreen = MutableLiveData<Screen>(Screen.List)
    val currentScreen: LiveData<Screen> = _currentScreen

    fun setScreen(screen: Screen) {
        _currentScreen.value = screen
    }

}
package com.danzeevi.flashcards.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


fun ViewModel.collectEvents(eventBus: EventBus, collector: (event: AppEvent) -> Unit) {
    viewModelScope.launch {
        eventBus.events.collect(collector)
    }
}
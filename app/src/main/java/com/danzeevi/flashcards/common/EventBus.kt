package com.danzeevi.flashcards.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBus {
    private val _events = MutableSharedFlow<AppEvent>(replay = 1)
    val events = _events.asSharedFlow()

    suspend fun post(event: AppEvent) {
        _events.emit(event)
    }
}

sealed class AppEvent {
    object EmptyEvent : AppEvent()
    data class DeepLinkAddValue(val string: String) : AppEvent()
}

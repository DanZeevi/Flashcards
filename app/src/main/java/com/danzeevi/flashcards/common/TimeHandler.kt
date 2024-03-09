package com.danzeevi.flashcards.common

interface TimeHandler {
    fun getCurrentTimeMs(): Long
}

class TimeHandlerDefault: TimeHandler {
    override fun getCurrentTimeMs(): Long = System.currentTimeMillis()
}
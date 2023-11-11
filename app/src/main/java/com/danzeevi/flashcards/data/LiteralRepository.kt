package com.danzeevi.flashcards.data

import androidx.lifecycle.LiveData

interface LiteralRepository {
    fun getAll(): LiveData<List<Literal>>
    fun insert(literal: Literal)
    fun update(literal: Literal)
    fun delete(literal: Literal)
}
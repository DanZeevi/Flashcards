package com.danzeevi.flashcards.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class LiteralRepositoryImpl(private val literalDAO: LiteralDAO): LiteralRepository {
    override fun getAll(): Flow<List<Literal>> = literalDAO.getAll()

    override fun insert(literal: Literal) {
        literalDAO.insert(literal)
    }

    override fun update(literal: Literal) {
        literalDAO.update(literal)
    }

    override fun delete(literal: Literal) {
        literalDAO.delete(literal)
    }

}
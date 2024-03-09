package com.danzeevi.flashcards.data

import com.danzeevi.flashcards.common.TimeHandler
import kotlinx.coroutines.flow.Flow

class LiteralRepositoryImpl(private val literalDAO: LiteralDAO, private val timeHandler: TimeHandler) : LiteralRepository {
    override fun getAll(): Flow<List<Literal>> = literalDAO.getAll()

    override fun insert(literal: Literal) {
        literalDAO.insert(literal)
    }

    override suspend fun update(literal: Literal) {
        literalDAO.update(literal)
    }

    override fun delete(literal: Literal) {
        literalDAO.delete(literal)
    }

    override fun getLiteralsFilteredForToday() = literalDAO.getLiteralsBeforeNow(timeHandler.getCurrentTimeMs())
}
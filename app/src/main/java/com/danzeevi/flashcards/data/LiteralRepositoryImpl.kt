package com.danzeevi.flashcards.data

import androidx.lifecycle.LiveData

class LiteralRepositoryImpl(private val literalDAO: LiteralDAO): LiteralRepository {
    override fun getAll(): LiveData<List<Literal>> = literalDAO.getAll()

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
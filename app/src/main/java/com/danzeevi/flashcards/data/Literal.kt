package com.danzeevi.flashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Literal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val value: String,
    val definition: String
)

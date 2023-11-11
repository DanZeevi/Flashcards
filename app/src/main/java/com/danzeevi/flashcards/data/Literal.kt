package com.danzeevi.flashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Literal(
    val value: String,
    val definition: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

package com.danzeevi.flashcards.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Literal(
    var value: String,
    var definition: String,
    var example: String? = null,
    var nextViewDate: Long = 0,
    var interval: Int = 1,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)

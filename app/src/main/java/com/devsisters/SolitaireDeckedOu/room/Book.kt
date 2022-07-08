package com.devsisters.SolitaireDeckedOu.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = false)
    val bookId: Int = 0,
    @ColumnInfo(name = "text")
    val bookText: String,
    @ColumnInfo(name = "saved")
    val bookSaved: Boolean
)
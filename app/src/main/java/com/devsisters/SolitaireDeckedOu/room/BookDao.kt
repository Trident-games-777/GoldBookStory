package com.devsisters.SolitaireDeckedOu.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * FROM books LIMIT 1")
    suspend fun takeBook(): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putBook(book: Book)

}
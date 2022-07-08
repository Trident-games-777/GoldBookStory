package com.devsisters.SolitaireDeckedOu.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDb : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile
        private var instance: BookDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context, prePopulateScope: CoroutineScope) =
            instance ?: synchronized(LOCK) {
                instance ?: createDatabase(context, prePopulateScope).also { instance = it }
            }

        private fun createDatabase(context: Context, scope: CoroutineScope) = Room.databaseBuilder(
            context.applicationContext,
            BookDb::class.java,
            "book_db.db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { database ->
                    scope.launch {
                        database.bookDao().putBook(
                            Book(bookText = "gooldbooksor.xyz/storys.php", bookSaved = false)
                        )
                    }
                }
            }
        }
        ).build()
    }
}
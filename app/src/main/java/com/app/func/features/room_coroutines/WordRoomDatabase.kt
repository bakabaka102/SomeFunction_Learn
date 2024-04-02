package com.app.func.features.room_coroutines

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(private val scope: CoroutineScope) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            _instance?.let { database ->
                scope.launch {
                    val wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Word(word = "Hello")
                    wordDao.insert(word)
                    word = Word(word = "World!")
                    wordDao.insert(word)

                    // TODO: Add your own words!
                    word = Word(word = "TODO!")
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var _instance: WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return _instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                _instance = instance
                instance
            }
        }
    }
}

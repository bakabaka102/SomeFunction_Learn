package com.app.func.features.room_database_sqlite_mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 100, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var _instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase = synchronized(this) {
            var instance = _instance
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, NoteDatabase::class.java, "note_database")
                        .fallbackToDestructiveMigration().build()
            }
            _instance = instance
            return instance
        }
    }
}
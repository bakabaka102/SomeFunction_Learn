package com.app.func.features.room_database_sqlite_mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.func.features.room_database_sqlite_mvvm.utils.Utils.subscribeOnBackground

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

        private fun populateDatabase(db: NoteDatabase) {
            val noteDao = db.noteDao()
            subscribeOnBackground {
                noteDao.insert(Note("title 1", "desc 1", 1))
                noteDao.insert(Note("title 2", "desc 2", 2))
                noteDao.insert(Note("title 3", "desc 3", 3))

            }
        }
    }


}
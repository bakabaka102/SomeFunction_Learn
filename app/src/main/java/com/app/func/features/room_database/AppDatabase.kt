package com.app.func.features.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//@Database(entities = arrayOf(User::class), version = 101, exportSchema = false)
@Database(entities = [User::class], version = 101, exportSchema = false)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null

        fun initInstanceDatabase(context: Context): AppDatabase = synchronized(this) {
            var instance = _instance
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user.db"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            _instance = instance
            return instance
        }

        fun destroyInstance() {
            _instance = null
        }
    }
}
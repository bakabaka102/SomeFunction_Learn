package com.app.func.features.room_coroutines

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): List<Word>

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun loadAllWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word): Long

    @Update
    suspend fun update(word: Word): Int

    @Delete
    suspend fun delete(word: Word): Int

    @Query("DELETE FROM word_table")
    suspend fun deleteAll(): Int
}
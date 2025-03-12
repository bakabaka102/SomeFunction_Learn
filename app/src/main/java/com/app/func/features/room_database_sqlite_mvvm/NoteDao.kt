package com.app.func.features.room_database_sqlite_mvvm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note): Long

    @Update
    fun update(note: Note): Int

    @Delete
    fun delete(note: Note): Int

    @Query("DELETE FROM note_table")
    fun deleteAllNotes(): Int

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotesByDesc(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}
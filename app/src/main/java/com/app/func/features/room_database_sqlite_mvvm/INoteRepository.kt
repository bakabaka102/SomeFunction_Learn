package com.app.func.features.room_database_sqlite_mvvm

import androidx.lifecycle.LiveData

interface INoteRepository {

    fun insert(note: Note)

    fun update(note: Note)

    fun delete(note: Note)

    fun deleteAllNotes()

    fun getAllNotes(): LiveData<List<Note>>?
}
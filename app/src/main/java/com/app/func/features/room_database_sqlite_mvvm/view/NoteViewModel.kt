package com.app.func.features.room_database_sqlite_mvvm.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.func.features.room_database_sqlite_mvvm.Note
import com.app.func.features.room_database_sqlite_mvvm.NoteRepositoryImpl

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = NoteRepositoryImpl(application)
    val allNotes: LiveData<List<Note>>? = repository.getAllNotes()

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}
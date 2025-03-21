package com.app.func.features.room_database_sqlite_mvvm

import android.content.Context
import androidx.lifecycle.LiveData
import com.app.func.features.room_database_sqlite_mvvm.utils.Utils.subscribeOnBackground

class NoteRepositoryImpl(context: Context) : INoteRepository {

    private val database = NoteDatabase.getInstance(context.applicationContext)
    private val noteDao: NoteDao = database.noteDao()

    override fun insert(note: Note) {
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        subscribeOnBackground {
            noteDao.insert(note)
        }
    }

    override fun update(note: Note) {
        subscribeOnBackground {
            noteDao.update(note)
        }
    }

    override fun delete(note: Note) {
        subscribeOnBackground {
            noteDao.delete(note)
        }
    }

    override fun deleteAllNotes() {
        subscribeOnBackground {
            noteDao.deleteAllNotes()
        }
    }

    override fun getAllNotes(): LiveData<List<Note>>? {
        var allNote : LiveData<List<Note>>? = null
        subscribeOnBackground {
            allNote = noteDao.getAllNotes()
        }
        return allNote
    }
}
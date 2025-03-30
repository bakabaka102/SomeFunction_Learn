package com.app.func.features.room_coroutines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel(app: Application) : AndroidViewModel(app) {

    private val db = WordRoomDatabase.getDatabase(app.applicationContext)
    val wordDao = db.wordDao()
    private val repository = WordRepository(wordDao)
    val allWords get() = repository.allWords
    val wordState get() = repository.wordState

    fun getAllWordsInDB() {
        viewModelScope.launch {
            repository.allWords()
        }
    }

    fun insert(word: Word?): Long {
        var result = 0L
        viewModelScope.launch {
            word?.let {
                result = repository.insert(it)
            }
        }
        return result
    }

    fun update(word: Word?): Int {
        var result = 0
        viewModelScope.launch {
            word?.let {
                result = repository.update(it)
            }
        }

        return result
    }

    fun delete(word: Word?): Int {
        var result = 0
        viewModelScope.launch {
            word?.let {
                result = repository.delete(it)
            }
        }
        return result
    }
}
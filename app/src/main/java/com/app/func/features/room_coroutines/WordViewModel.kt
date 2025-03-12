package com.app.func.features.room_coroutines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel(app: Application) : AndroidViewModel(app) {

    private val db = WordRoomDatabase.getDatabase(app.applicationContext)
    val wordDao = db.wordDao()
    private val repository = WordRepository(wordDao)
    val allWords: LiveData<List<Word>> get() = repository.allWords
    fun getAllWordsInDB() {
        viewModelScope.launch {
            repository.allWords()
        }
    }

    fun insert(word: Word?) = viewModelScope.launch {
        word?.let {
            repository.insert(it)
        }
    }
}
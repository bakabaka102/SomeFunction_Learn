package com.app.func.features.room_coroutines

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class WordRepository(private val wordDao: WordDao) : IWordRepository {

    private val _allWords: MutableLiveData<List<Word>> = MutableLiveData<List<Word>>()
    val allWords: LiveData<List<Word>> get() = _allWords

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override suspend fun allWords() {
        withContext(Dispatchers.IO) {
            try {
                _allWords.postValue(wordDao.getAlphabetizedWords())
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        }
    }

    override suspend fun allByFlow() {
        withContext(Dispatchers.IO) {
            try {
                wordDao.loadAllWords().collect {
                    _allWords.postValue(it)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    // By default, Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long-running database work
    // off the main thread.
    @WorkerThread
    override suspend fun insert(word: Word): Long {
        return wordDao.insert(word)
    }

    override suspend fun update(word: Word): Int {
        return wordDao.update(word)
    }

    override suspend fun delete(word: Word): Int {
        return wordDao.delete(word)
    }

    override suspend fun deleteAll(): Int {
        return wordDao.deleteAll()
    }
}

interface IWordRepository {

    suspend fun allWords()

    suspend fun allByFlow()

    suspend fun insert(word: Word): Long

    suspend fun update(word: Word): Int

    suspend fun delete(word: Word): Int

    suspend fun deleteAll(): Int
}
package com.app.func.features.room_coroutines.adapters

import com.app.func.features.room_coroutines.Word

interface RestoreRemoveListener {
    fun onRestoreClick(word: Word)
}
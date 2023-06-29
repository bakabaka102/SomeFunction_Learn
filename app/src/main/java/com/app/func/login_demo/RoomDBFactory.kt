package com.app.func.login_demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.func.features.room_database.UserRepository

class RoomDBFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        return super.create(modelClass)

    }
}
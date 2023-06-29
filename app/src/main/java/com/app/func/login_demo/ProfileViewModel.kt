package com.app.func.login_demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.func.features.room_database.User
import com.app.func.features.room_database.UserRepository

class ProfileViewModel(userRepository: UserRepository) : ViewModel() {

    val allUser = userRepository.getAllUsers()

    val getUsersWithAsc: LiveData<List<User>>? = userRepository.getUserAsc()
}
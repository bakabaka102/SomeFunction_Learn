package com.app.func.login_demo

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.func.features.room_database.AppDatabase
import com.app.func.features.room_database.User
import com.app.func.features.room_database.UserRepository

class RoomViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.initInstanceDatabase(application)
    private val repository: UserRepository by lazy {
        UserRepository(database)
    }

    val getUsersWithAsc: LiveData<List<User>>? = repository.getUserAsc()

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

    fun insertUser(user: User) {
        Handler(Looper.getMainLooper()).post {
            database.userDao().insertUser(user)
        }
    }

    fun updateUser(user: User) {
        database.userDao().updateUser(user)
    }

    fun getAllUsers(): List<User> {
        //return dao?.getUsers()
        //return dao?.getUsersWithAsc()
        return database.userDao().getUsersWithDesc()
    }
}
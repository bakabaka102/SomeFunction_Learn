package com.app.func.features.room_database

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper

class UserRepository(context: Context) {

    var dao: UserDao? = AppDatabase.newInstance(context)?.userDao()
    val handler: Handler = Handler(Looper.getMainLooper())

    //Fetch All the Users
    fun getAllUsers(): List<User>? {
        return dao?.getUsers()
    }

    // Insert new user
    fun insertUser(user: User) {
        handler.post {
            dao?.insertUser(user)
        }
    }

    fun updateUser(user: User) {
        dao?.updateUser(user)
    }

    fun deleteUser(users: User) {
        dao?.deleteUser(users)
    }


}
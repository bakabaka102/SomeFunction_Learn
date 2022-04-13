package com.app.func.features.room_database

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User)

    @Query("select * from user")
    fun getUsers() : List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

}
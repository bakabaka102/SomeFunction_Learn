package com.app.func.features.room_database

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun suspendInsertUser(user: User)

    @Insert
    fun insertUser(user: User)

    @Query("select * from user")
    fun getUsers(): List<User>

    @Query("select * from user order by userName asc")
    fun getUsersWithAsc(): List<User>

    @Query("select * from user order by userName desc")
    fun getUsersWithDesc(): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

//    @Query("delete from user")
//    suspend fun deleteAllUser()

}
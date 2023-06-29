package com.app.func.features.room_database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun suspendInsertUser(user: User)

    @Insert
    fun insertUser(user: User)

    @Query("select * from user")
    fun getUsers(): List<User>

    @Query("select * from user order by userName asc")
    fun getUsersWithAsc(): LiveData<List<User>>

    @Query("select * from user order by userName desc")
    fun getUsersWithDesc(): List<User>

    @Query("SELECT * FROM user WHERE userId = :id")
    fun getUserById(id: Int?): User?

    //    @Query("SELECT * FROM user WHERE userName LIKE :name")
    @Query("SELECT * FROM user WHERE userName IN (:name)")
    fun getUserByName(name: String?): User?

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

//    @Query("delete from user")
//    suspend fun deleteAllUser()

}
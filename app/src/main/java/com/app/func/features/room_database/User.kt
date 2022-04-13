package com.app.func.features.room_database

//https://github.com/velmurugan-murugesan/Android-Example
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Int? = null,
    val userName: String,
    var location: String,
    val email: String
)
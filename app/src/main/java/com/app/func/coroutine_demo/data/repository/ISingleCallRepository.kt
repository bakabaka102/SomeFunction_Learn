package com.app.func.coroutine_demo.data.repository

interface ISingleCallRepository {

    suspend fun getAllQuotes()
}

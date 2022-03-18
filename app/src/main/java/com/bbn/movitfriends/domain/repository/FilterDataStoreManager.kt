package com.bbn.movitfriends.domain.repository

interface FilterDataStoreManager {

    suspend fun saveString(key: String, value: String)

    suspend fun saveInt(key: String, value: Int)

    suspend fun readString(key: String): String?

    suspend fun readInt(key: String): Int?
}
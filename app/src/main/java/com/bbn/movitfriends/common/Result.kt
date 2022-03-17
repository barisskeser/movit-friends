package com.bbn.movitfriends.common

sealed class Result (val message: String? = null) {
    class Success: Result()
    class Error (message: String): Result (message)
}
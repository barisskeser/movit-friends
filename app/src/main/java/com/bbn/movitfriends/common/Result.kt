package com.bbn.movitfriends.common

sealed class Result (message: String? = null) {
    class Success: Result()
    class Error (message: String): Result (message)
}
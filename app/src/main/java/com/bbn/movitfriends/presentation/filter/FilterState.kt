package com.bbn.movitfriends.presentation.filter

data class FilterState(
    val beginAge: Int = 20,
    val endAge: Int = 30,
    val gender: String = "Both",
    val distance: Int = 50,
    val status: String = "Any"
)
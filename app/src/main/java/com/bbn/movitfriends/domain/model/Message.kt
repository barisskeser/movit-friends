package com.bbn.movitfriends.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.database.ServerValue
import java.util.*

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val from: String = "",
    val to: String = "",
    val text: String = "",
    val time: Long = System.currentTimeMillis(),
    val isSeen: Boolean = false
) {

}


class Message1 {
    private var id: String = UUID.randomUUID().toString()
        get() = field
    private var from: String = ""
        get() = field
    private var to: String = ""
        get() = field
    private var text: String = ""
        get() = field
    private var isSeen: Boolean = false
        get() = field
    private var time: Timestamp = Timestamp.now()
        get() = field

    fun setTimestamp(second: Long, nanosecond: Int) {
        this.time = Timestamp(second, nanosecond)
    }

    constructor(
        from: String,
        to: String,
        text: String,
        isSeen: Boolean,
    ) {
        this.from = from
        this.to = to
        this.text = text
        this.isSeen = isSeen
    }

    constructor()
}
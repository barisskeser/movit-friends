package com.bbn.movitfriends.domain.model

import java.sql.Timestamp

data class Request(
    val time: Timestamp,
    val from: String,
    val to: String,
    val status: String
)

fun Request.toHashMap(): HashMap<String, Any> {
    val map = HashMap<String, Any>()
    map.put("time", this.time)
    map.put("from", this.from)
    map.put("to", this.to)
    map.put("status", this.status)

    return map
}
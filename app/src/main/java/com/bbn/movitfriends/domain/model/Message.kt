package com.bbn.movitfriends.domain.model

data class Message(
    val id: String,
    val from: String,
    val to: String,
    val text: String,
    val time: com.google.firebase.Timestamp,
    val isSeen: Boolean
)

fun Message.toHashMap(): HashMap<String, Any>{
    val map = HashMap<String, Any>()
    map.put("id", this.id)
    map.put("from", this.from)
    map.put("to", this.to)
    map.put("message", this.text)
    map.put("time", this.time)
    map.put("isSeen", this.isSeen)

    return map
}
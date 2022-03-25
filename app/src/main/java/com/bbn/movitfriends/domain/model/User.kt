package com.bbn.movitfriends.domain.model

import com.google.type.DateTime
import java.util.*
import kotlin.collections.HashMap

data class User(
    val status: String = "Offline",
    val id: String,
    val username: String = "",
    val fullName: String = "",
    val gender: String = "None",
    val birthDate: DateTime? = DateTime.getDefaultInstance(),
    val email: String,
    val lat: Double? = null,
    val lng: Double? = null,
    val isAdBlocked: Boolean = false,
    val isPremium: Boolean = false,
    val imageUrl: String = "default",
    val credit: Int = 0,
    val createDate: String = DateTime.getDefaultInstance().toString(),
    val about: String = "",
    val age: Int = 0
)

fun User.toHashMap(): HashMap<String, Any>{
    val map = HashMap<String, Any>()
    map.put("id", this.id)
    map.put("username", this.username)
    map.put("fullName", this.fullName)
    map.put("gender", this.gender)
    map.put("email", this.email)
    map.put("status", this.status)
    map.put("isAdBlocked", this.isAdBlocked)
    map.put("isPremium", this.isPremium)
    map.put("imageUrl", this.imageUrl)
    map.put("credit", this.credit)
    map.put("createDate", this.createDate)
    map.put("about", this.about)
    map.put("age", birthDate?.toAge() ?: this.age)

    return map
}

fun DateTime.toAge(): Int {
    return DateTime.getDefaultInstance().year - this.year
}
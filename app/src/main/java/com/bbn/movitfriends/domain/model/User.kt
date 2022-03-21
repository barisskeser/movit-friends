package com.bbn.movitfriends.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.type.DateTime
import java.sql.Time
import java.util.*
import kotlin.collections.HashMap

data class User(
    val status: String = "Offline",
    var id: String = UUID.randomUUID().toString(),
    val username: String = "",
    val fullName: String = "",
    val gender: String = "None",
    val birthDate: DateTime?,
    val email: String,
    val lat: Double? = null,
    val lng: Double? = null,
    val isAdBlocked: Boolean = false,
    val isPremium: Boolean = false,
    val imageUrl: String = "",
    val credit: Int = 0,
    val createDate: String = DateTime.getDefaultInstance().toString(),
    val about: String = ""
)

fun User.toHashMap(): HashMap<String, Any>{
    val map = HashMap<String, Any>()
    map.put("id", this.id)
    map.put("username", this.username)
    map.put("fullName", this.fullName)
    map.put("gender", this.gender)
    map.put("age", this.birthDate!!)
    map.put("email", this.email)
    map.put("lat", this.lat!!)
    map.put("lng", this.lng!!)
    map.put("isAdBlocked", this.isAdBlocked)
    map.put("isPremium", this.isPremium)
    map.put("imageUrl", this.imageUrl)
    map.put("credit", this.credit)
    map.put("createDate", this.createDate!!)
    map.put("about", this.about)

    return map
}

fun User.setId(id: String){
    this.id = id
}